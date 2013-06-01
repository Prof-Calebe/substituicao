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
    
    public String notificarAusencia(Long idProfessor, String dataInicio, String dataFim, String motivo, Long idProfessorSubstituto) throws ParseException {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        
        DateTime inicio = new DateTime(sdf.parse(dataInicio));
        DateTime fim = new DateTime(sdf.parse(dataFim));
        
        Interval periodo = new Interval(inicio, fim);
        
        Professor professor = profController.findProfessor(idProfessor);
        Professor professorSubstituto = profController.findProfessor(idProfessorSubstituto);
        
        Random r = new Random();
        
        String codigo = Integer.toString(r.nextInt(10000));
        
        Ausencia ausencia = new Ausencia(codigo, periodo, professor, motivo);
        ausencia.indicarSubstituto(professorSubstituto);
        
        
        ausenciaController.create(ausencia);
        
        return codigo;
        
    }

    public void editarAusencia(String codigo, String dataInicio, String dataFim, String motivo, Long idSubstituto) throws ParseException, NonexistentEntityException, Exception {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        
        DateTime inicio = new DateTime(sdf.parse(dataInicio));
        DateTime fim = new DateTime(sdf.parse(dataFim));
        
        Interval periodo = new Interval(inicio, fim);
        
        Professor professorSubstituto = profController.findProfessor(idSubstituto);
        
        Random r = new Random();
        
        Ausencia ausencia = ausenciaController.findAusencia(codigo);
        ausencia.setPeriodo(periodo);
        ausencia.setMotivo(motivo);
        ausencia.indicarSubstituto(professorSubstituto);
                
        ausenciaController.edit(ausencia);
    }

    public List<AusenciaModel> listarAusencias() {
        
        List<Ausencia> ausencias = ausenciaController.findAusenciaEntities();
        List<AusenciaModel> modelos = new LinkedList<AusenciaModel>();
        
        for(Ausencia ausencia : ausencias){
            
            AusenciaModel modelo = new AusenciaModel();
            
            modelo.codigo = ausencia.getCodigo();
            modelo.professorAusente = ausencia.getProfessor().getNome();
            modelo.professorSubstituto = ausencia.getIndicacaoSubstituto().getNome();
            modelo.estado = this.determinarEstado(ausencia.getEstado());
            modelo.id = ausencia.getId();
            Interval p = ausencia.getPeriodo();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            modelo.dataInicio = sdf.format(p.getStart().toDate());
            modelo.dataInicio = sdf.format(p.getEnd().toDate());
            
            modelos.add(modelo);
        }
        
        return modelos;
    }
    
    private String determinarEstado(EstadoAusencia estado){
        if(estado == EstadoAusencia.Alocacao_Cancelada)
            return "Alocação cancelada";
        else if(estado == EstadoAusencia.Alocacao_Efetuada)
            return "Alocação efetuada";
        else if(estado == EstadoAusencia.Alocacao_Pendente)
            return "Alocação pendente";
        else{
            return "Aulas canceladas";
        }
    }
    
    public List<AusenciaModel> listarAusenciasPorEstado(EstadoAusencia estado){
        
        List<Ausencia> ausencias = ausenciaController.findAusenciaEntities();
        List<AusenciaModel> modelos = new ArrayList<AusenciaModel>();
        
        for(Ausencia ausencia : ausencias){
            
            if(ausencia.getEstado().equals(estado)){
                AusenciaModel modelo = new AusenciaModel();
                
                modelo.codigo = ausencia.getCodigo();
                modelo.professorAusente = ausencia.getProfessor().getNome();
                modelo.professorSubstituto = ausencia.getIndicacaoSubstituto().getNome();
                modelo.estado = this.determinarEstado(ausencia.getEstado());
                modelo.id = ausencia.getId();
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Interval periodo = ausencia.getPeriodo();
                modelo.dataInicio = sdf.format(periodo.getStart().toDate());
                modelo.dataFim = sdf.format(periodo.getEnd().toDate());
                
                modelos.add(modelo);   
            }               
        }            
        return modelos;   
    }
    

}
