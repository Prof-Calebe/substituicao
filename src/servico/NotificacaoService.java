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
    
    private final AusenciaJpaController ausenciaController;

    private final ProfessorJpaController profController;
    
    public NotificacaoService(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        ausenciaController = new AusenciaJpaController(emf);
//        periodoController = new PeriodoJpaController(emf);
        profController = new ProfessorJpaController(emf);
    }
    
    public String notificarAusencia(Long idProfessor, String dataInicio, String dataFim, String motivo, List<String> nomesProfessoresIndicados) throws ParseException {
        
        SimpleDateFormat sdf = null;
        
        DateTime inicio = null;
        DateTime fim = null;
        
        try {
            sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            inicio = new DateTime(sdf.parse(dataInicio));
            fim = new DateTime(sdf.parse(dataFim));
        } catch (ParseException pe) {
            sdf = new SimpleDateFormat("dd/MM/yyyy");
            inicio = new DateTime(sdf.parse(dataInicio)).withHourOfDay(0).withMinuteOfHour(0);
            fim = new DateTime(sdf.parse(dataFim)).withHourOfDay(23).withMinuteOfHour(59);
        }

        Interval periodo = new Interval(inicio, fim);
        
        Professor professor = profController.findProfessor(idProfessor);
        
        Random r = new Random();
               
        List<Ausencia> ausencias = professor.gerarAusencias(periodo, motivo);
        
        List<String> codigos = new LinkedList<>();
        
        for(Ausencia ausencia : ausencias)
        {
            String codigo = Integer.toString(r.nextInt(10000));
            
            ausencia.setCodigo(codigo);
            
            codigos.add(codigo);
            
            for(String nomeProf : nomesProfessoresIndicados){
                Professor professorIndicado = profController.findProfessor(nomeProf);
                ausencia.indicarSubstituto(professorIndicado);
            }

            ausenciaController.create(ausencia);
        }
        
        if(codigos.size() > 0)
            return codigos.get(0);       
        else
            return "0";
    }

    public List<Ausencia> listarAusencias() {
        
        List<Ausencia> ausencias = ausenciaController.findAusenciaEntities();
        List<Ausencia> modelos = new LinkedList<>();
        
        for(Ausencia ausencia : ausencias){
            
            Ausencia modelo = this.montarAusencia(ausencia);
            
            modelos.add(modelo);
        }
        
        return modelos;
    }
    
    /*
    public List<Ausencia> listarAusenciasPorEstado(EstadoAusencia estado){
        
        List<Ausencia> ausencias = ausenciaController.findAusenciaEntities();
        List<Ausencia> modelos = new ArrayList<Ausencia>();
        
        for(Ausencia ausencia : ausencias){
            
            if(ausencia.getEstado().equals(estado)){
                Ausencia modelo = this.montarAusencia(ausencia);
                
                modelos.add(modelo);   
            }               
        }            
        return modelos;   
    }
    */
    
    public List<Ausencia> listarAusenciasPorProfessor(String usernameProfessor){
        
        Professor professor = profController.findProfessorPorUsername(usernameProfessor);
        
        List<Ausencia> ausenciasPorProfessor = ausenciaController.listAusenciasPorProfessor(professor);
        
        List<Ausencia> modelos = new ArrayList<>();
        
        for(Ausencia ausencia : ausenciasPorProfessor){
            
            Ausencia modelo = this.montarAusencia(ausencia);
            
            modelos.add(modelo);
            
        }
        
        return modelos;
    }
    
    public List<Ausencia> listarAusenciasPorIndicacaoDeSubstituto(String usernameProfessor){
        
        Professor professorIndicado = profController.findProfessorPorUsername(usernameProfessor);
        
        List<Ausencia> ausenciasComIndicacaoDeSubstituto = ausenciaController.listAusenciasPorIndicacaoDeSubstituto(professorIndicado);
        
        List<Ausencia> modelos = new ArrayList<>();
        
        for(Ausencia ausencia : ausenciasComIndicacaoDeSubstituto){
            
            Ausencia modelo = this.montarAusencia(ausencia);
            
            modelos.add(modelo);
            
        }
        
        return modelos;
    }
    
    public List<Ausencia> listarAusenciasPorSubstituto(String usernameProfessor){
        
        Professor professorIndicado = profController.findProfessorPorUsername(usernameProfessor);
        
        List<Ausencia> ausenciasComIndicacaoDeSubstituto = ausenciaController.listAusenciasPorSubstituto(professorIndicado);
        
        List<Ausencia> modelos = new ArrayList<>();
        
        for(Ausencia ausencia : ausenciasComIndicacaoDeSubstituto){
            
            Ausencia modelo = this.montarAusencia(ausencia);
            
            modelos.add(modelo);
            
        }
        
        return modelos;
    }
    
    public void aceitarSubstituicao(Long ausenciaId)
    {        
        try {
            Ausencia ausencia = ausenciaController.findAusencia(ausenciaId);
            ausencia.confirmar();
            ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void recusarSubstituicao(Long ausenciaId)
    {
        try {
            Ausencia ausencia = ausenciaController.findAusencia(ausenciaId);
            ausencia.recusar();
            ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private Ausencia montarAusencia(Ausencia ausencia){

        Ausencia modelo = new Ausencia();

        modelo.setCodigo(ausencia.getCodigo());
        modelo.setProfessor(ausencia.getProfessor());
        
        if(ausencia.getProfessorSubstituto() != null){
            modelo.setProfessorSubstituto(ausencia.getProfessorSubstituto());    
        }
        
        //modelo.professorSubstituto = ausencia.getIndicacoesSubstitutos().getNome();
        modelo.setEstado(ausencia.getEstado());
        modelo.setId(ausencia.getId());
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Interval periodo = ausencia.getPeriodo();
        modelo.setPeriodo(periodo);
        
        
        return modelo;
        
    }

    public void definirSubstituto(String codigo, String nomeProfessor) {
        
        Professor profSubstituto = profController.findProfessor(nomeProfessor);

        if(profSubstituto == null){
            throw new IllegalStateException("Professor de nome " + nomeProfessor + " não existe");
        }
        
        Ausencia ausencia = ausenciaController.findAusencia(codigo);
        
        
        if(ausencia == null){
            throw new IllegalStateException("Ausência de código " + codigo + " não existe");
        }        
        
        ausencia.setProfessorSubstituto(profSubstituto);
        
        try {
            ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        }

        
    }
    
    public void cancelarAusencia(String codigo){
        
        Ausencia ausencia = ausenciaController.findAusencia(codigo);
        
        ausencia.cancelarAusencia();
        try {
            ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void cancelarAulas(String codigo){
        
        Ausencia ausencia = ausenciaController.findAusencia(codigo);
        
        ausencia.cancelarAulas();
        try {
            ausenciaController.edit(ausencia);
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(NotificacaoService.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

}
