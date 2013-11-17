/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.AusenciaJpaController;
import datamapper.ProfessorJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Ausencia;
import dominio.EstadoAusencia;
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
    
    private AusenciaJpaController ausenciaController;
    private ProfessorJpaController profController;
    
    public NotificacaoService(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        ausenciaController = new AusenciaJpaController(emf);
//        periodoController = new PeriodoJpaController(emf);
        profController = new ProfessorJpaController(emf);
    }
    
    public String notificarAusencia(Long idProfessor, String dataInicio, String dataFim, String motivo, List<String> nomesProfessoresIndicados) throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        
        DateTime inicio = new DateTime(sdf.parse(dataInicio));
        DateTime inícioReal = new DateTime(inicio.getYear(),inicio.getMonthOfYear(),inicio.getDayOfMonth(),00,00);
        
        DateTime fim = new DateTime(sdf.parse(dataFim));
        DateTime fimReal = new DateTime(fim.getYear(),fim.getMonthOfYear(),fim.getDayOfMonth(),23,59);
        
        Interval periodo = new Interval(inícioReal, fimReal);
        
        Professor professor = profController.findProfessor(idProfessor);
        List<Professor> professoresIndicados = new ArrayList<Professor>();
        
        //Professor professorSubstituto = profController.findProfessor(idProfessorSubstituto);
        
        Random r = new Random();
               
        List<Ausencia> ausencias = professor.gerarAusencias(periodo, motivo);
        
        List<String> codigos = new LinkedList<String>();
        
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

//    public void editarAusencia(String codigo, String dataInicio, String dataFim, String motivo, Long idSubstituto) throws ParseException, NonexistentEntityException, Exception {
//        
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        
//        DateTime inicio = new DateTime(sdf.parse(dataInicio));
//        DateTime fim = new DateTime(sdf.parse(dataFim));
//        
//        Interval periodo = new Interval(inicio, fim);
//        
//        Professor professorSubstituto = profController.findProfessor(idSubstituto);
//        
//        Random r = new Random();
//        
//        Ausencia ausencia = ausenciaController.findAusencia(codigo);
//        ausencia.setPeriodo(periodo);
//        ausencia.setMotivo(motivo);
//        ausencia.indicarSubstituto(professorSubstituto);
//                
//        ausenciaController.edit(ausencia);
//    }

    public List<AusenciaModel> listarAusencias() {
        
        List<Ausencia> ausencias = ausenciaController.findAusenciaEntities();
        List<AusenciaModel> modelos = new LinkedList<AusenciaModel>();
        
        for(Ausencia ausencia : ausencias){
            
            AusenciaModel modelo = this.montarAusencia(ausencia);
            
            modelos.add(modelo);
        }
        
        return modelos;
    }
    
    private String determinarEstado(EstadoAusencia estado){
        if(estado == EstadoAusencia.Ausencia_Cancelada)
            return "Ausência cancelada";
        else if(estado == EstadoAusencia.Alocacao_Efetuada)
            return "Alocação efetuada";
        else if(estado == EstadoAusencia.Alocacao_Pendente)
            return "Alocação pendente";
        else{
            return "Aulas canceladas";
        }
    }
    
    /*
    public List<AusenciaModel> listarAusenciasPorEstado(EstadoAusencia estado){
        
        List<Ausencia> ausencias = ausenciaController.findAusenciaEntities();
        List<AusenciaModel> modelos = new ArrayList<AusenciaModel>();
        
        for(Ausencia ausencia : ausencias){
            
            if(ausencia.getEstado().equals(estado)){
                AusenciaModel modelo = this.montarAusencia(ausencia);
                
                modelos.add(modelo);   
            }               
        }            
        return modelos;   
    }
    */
    
    public List<AusenciaModel> listarAusenciasPorProfessor(String usernameProfessor){
        
        Professor professor = profController.findProfessorPorUsername(usernameProfessor);
        
        List<Ausencia> ausenciasPorProfessor = ausenciaController.listAusenciasPorProfessor(professor);
        
        List<AusenciaModel> modelos = new ArrayList<AusenciaModel>();
        
        for(Ausencia ausencia : ausenciasPorProfessor){
            
            AusenciaModel modelo = this.montarAusencia(ausencia);
            
            modelos.add(modelo);
            
        }
        
        return modelos;
    }
    
    public List<AusenciaModel> listarAusenciasPorIndicacaoDeSubstituto(String usernameProfessor){
        
        Professor professorIndicado = profController.findProfessorPorUsername(usernameProfessor);
        
        List<Ausencia> ausenciasComIndicacaoDeSubstituto = ausenciaController.listAusenciasPorIndicacaoDeSubstituto(professorIndicado);
        
        List<AusenciaModel> modelos = new ArrayList<AusenciaModel>();
        
        for(Ausencia ausencia : ausenciasComIndicacaoDeSubstituto){
            
            AusenciaModel modelo = this.montarAusencia(ausencia);
            
            modelos.add(modelo);
            
        }
        
        return modelos;
    }
    
    
    
    private AusenciaModel montarAusencia(Ausencia ausencia){

        AusenciaModel modelo = new AusenciaModel();

        modelo.codigo = ausencia.getCodigo();
        modelo.professorAusente = ausencia.getProfessor().getNome();
        
        if(ausencia.getProfessorSubstituto() != null){
            modelo.professorSubstituto = ausencia.getProfessorSubstituto().getNome();    
        }else{
            modelo.professorSubstituto = "";
        }
        
        //modelo.professorSubstituto = ausencia.getIndicacoesSubstitutos().getNome();
        modelo.estado = this.determinarEstado(ausencia.getEstado());
        modelo.id = ausencia.getId();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Interval periodo = ausencia.getPeriodo();
        modelo.dataInicio = sdf.format(periodo.getStart().toDate());
        modelo.dataFim = sdf.format(periodo.getEnd().toDate());
        
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
        ausencia.definirComoAlocado();
        
        
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
