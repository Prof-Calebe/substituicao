/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import datamapper.AusenciaJpaController;
import datamapper.ProfessorJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Ausencia;
import dominio.Professor;
import modelo.AusenciaModel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author Thiago Lima
 */
public class NotificacaoService {

    /**
     * Attribute:  ausenciaController
     */
    private final AusenciaJpaController ausenciaController;

    /**
     * Attribute:  profController
     */
    private final ProfessorJpaController profController;

    /**
     * Default constructor
     */
    public NotificacaoService() {
        EntityManagerFactory emf = 
                Persistence.createEntityManagerFactory("pro_subPU");
        this.ausenciaController = new AusenciaJpaController(emf);
        this.profController = new ProfessorJpaController(emf);
    }

    /**
     * Método para notificar ausência.
     * 
     * @param idProfessor
     * @param dataInicio
     * @param dataFim
     * @param motivo
     * @param nomesProfessoresIndicados
     * @return 
     * @throws java.text.ParseException
     */
    public String notificarAusencia(
            Long idProfessor,
            String dataInicio,
            String dataFim,
            String motivo,
            List<String> nomesProfessoresIndicados)
            throws ParseException {

        SimpleDateFormat sdf = null;

        DateTime inicio = null;
        DateTime fim = null;

        final int hour23 = 23;
        final String firstFormat = "dd/MM/yyyy HH:mm";
        final String seccondFormat = "dd/MM/yyyy";
        try {
            sdf = new SimpleDateFormat(firstFormat);
            inicio = new DateTime(sdf.parse(dataInicio));
            fim = new DateTime(sdf.parse(dataFim));
        } catch (ParseException pe) {
            sdf = new SimpleDateFormat(seccondFormat);
            inicio = new DateTime(sdf.parse(dataInicio)).withHourOfDay(0)
                    .withMinuteOfHour(0);
            fim = new DateTime(sdf.parse(dataFim)).withHourOfDay(hour23)
                    .withMinuteOfHour(59);
        }

        Interval periodo = new Interval(inicio, fim);

        Professor professor = this.profController.findProfessor(idProfessor);

        Random r = new Random();

        List<Ausencia> ausencias = professor.gerarAusencias(periodo, motivo);

        List<String> codigos = new LinkedList<>();

        final int num1 = 10000;
        for (Ausencia ausencia : ausencias) {
            String codigo = Integer.toString(r.nextInt(num1));

            ausencia.setCodigo(codigo);

            codigos.add(codigo);

            for (String nomeProf : nomesProfessoresIndicados) {
                Professor professorIndicado = 
                        this.profController.findProfessor(nomeProf);
                ausencia.indicarSubstituto(professorIndicado);
            }

            this.ausenciaController.create(ausencia);
        }

        if (codigos.size() > 0) {
            return codigos.get(0);
        } else {
            return "0";
        }
    }

    /**
     * Método para listar ausências.
     * 
     * @return 
     */
    public List<AusenciaModel> listarAusencias() {

        List<Ausencia> ausencias = this.ausenciaController.findAusenciaEntities();
        List<AusenciaModel> modelos = new LinkedList<>();

        for (Ausencia ausencia : ausencias) {

            AusenciaModel modelo = this.montarAusencia(ausencia);

            modelos.add(modelo);
        }

        return modelos;
    }

    /**
     * Método para listar ausências por professor.
     * 
     * @param usernameProfessor
     * @return 
     */
    public List<AusenciaModel> listarAusenciasPorProfessor(
            String usernameProfessor) {

        Professor professor = this.profController.findProfessorPorUsername(
                usernameProfessor);

        List<Ausencia> ausenciasPorProfessor = 
                this.ausenciaController.listAusenciasPorProfessor(professor);

        List<AusenciaModel> modelos = new ArrayList<>();

        for (Ausencia ausencia : ausenciasPorProfessor) {

            AusenciaModel modelo = this.montarAusencia(ausencia);

            modelos.add(modelo);

        }

        return modelos;
    }

    /**
     * Método para listar ausências por indicação de professor substituto.
     * 
     * @param usernameProfessor
     * @return 
     */
    public List<AusenciaModel> listarAusenciasPorIndicacaoDeSubstituto(
            String usernameProfessor) {

        Professor professorIndicado = 
                this.profController.findProfessorPorUsername(usernameProfessor);

        List<Ausencia> ausenciasComIndicacaoDeSubstituto = 
                this.ausenciaController.listAusenciasPorIndicacaoDeSubstituto(
                        professorIndicado);

        List<AusenciaModel> modelos = new ArrayList<>();

        for (Ausencia ausencia : ausenciasComIndicacaoDeSubstituto) {

            AusenciaModel modelo = this.montarAusencia(ausencia);

            modelos.add(modelo);

        }

        return modelos;
    }

    /**
     * Método para listar ausências por professor substituto.
     * 
     * @param usernameProfessor
     * @return 
     */
    public List<AusenciaModel> listarAusenciasPorSubstituto(
            String usernameProfessor) {

        Professor professorIndicado = 
                this.profController.findProfessorPorUsername(usernameProfessor);

        List<Ausencia> ausenciasComIndicacaoDeSubstituto = 
                this.ausenciaController.listAusenciasPorSubstituto(
                        professorIndicado);

        List<AusenciaModel> modelos = new ArrayList<>();

        for (Ausencia ausencia : ausenciasComIndicacaoDeSubstituto) {

            AusenciaModel modelo = this.montarAusencia(ausencia);

            modelos.add(modelo);

        }

        return modelos;
    }

    /**
     * Método para aceitar uma substituição.
     * 
     * @param ausenciaId 
     */
    public void aceitarSubstituicao(Long ausenciaId) {
        try {
            Ausencia ausencia = 
                    this.ausenciaController.findAusencia(ausenciaId);
            ausencia.confirmar();
            this.ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para recusar uma substituição.
     * 
     * @param ausenciaId 
     */
    public void recusarSubstituicao(Long ausenciaId) {
        try {
            Ausencia ausencia = 
                    this.ausenciaController.findAusencia(ausenciaId);
            ausencia.recusar();
            this.ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Método para montar uma ausência
     * 
     * @param ausencia
     * @return 
     */
    private AusenciaModel montarAusencia(Ausencia ausencia) {

        AusenciaModel modelo = new AusenciaModel();

        modelo.codigo = ausencia.getCodigo();
        modelo.professorAusente = ausencia.getProfessor().getNome();

        if (ausencia.getProfessorSubstituto() != null) {
            modelo.professorSubstituto = 
                    ausencia.getProfessorSubstituto().getNome();
        } else {
            modelo.professorSubstituto = "";
        }

        modelo.estado = ausencia.getEstado().getDescricao();
        modelo.id = ausencia.getId();
        final String firstFormat = "dd/MM/yyyy HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(firstFormat);
        Interval periodo = ausencia.getPeriodo();
        modelo.dataInicio = sdf.format(periodo.getStart().toDate());
        modelo.dataFim = sdf.format(periodo.getEnd().toDate());

        return modelo;

    }

    /**
     * Método para definir um professor substituto.
     * 
     * @param codigo
     * @param nomeProfessor 
     */
    public void definirSubstituto(String codigo, String nomeProfessor) {

        Professor profSubstituto = this.profController.findProfessor(nomeProfessor);
        final String naoExiste = " não existe";

        if (profSubstituto == null) {
            throw new IllegalStateException(
                    "Professor de nome " + nomeProfessor + naoExiste);
        }

        Ausencia ausencia = this.ausenciaController.findAusencia(codigo);

        if (ausencia == null) {
            throw new IllegalStateException(
                    "Ausência de código " + codigo + naoExiste);
        }

        ausencia.setProfessorSubstituto(profSubstituto);

        try {
            this.ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método para cancelar uma ausência.
     * 
     * @param codigo 
     */
    public void cancelarAusencia(String codigo) {

        Ausencia ausencia = this.ausenciaController.findAusencia(codigo);

        ausencia.cancelarAusencia();
        try {
            this.ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

    /**
     * Método para cancelar aulas.
     * 
     * @param codigo 
     */
    public void cancelarAulas(String codigo) {

        Ausencia ausencia = this.ausenciaController.findAusencia(codigo);

        ausencia.cancelarAulas();
        try {
            this.ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName())
                    .log(Level.SEVERE, null, ex);
        }

    }

}
