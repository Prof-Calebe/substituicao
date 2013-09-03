/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.ProfessorJpaController;
import datamapper.UsuarioJpaController;
import dominio.Aula;
import dominio.Professor;
import java.util.ArrayList;
import modelo.ProfessorModel;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.joda.time.Interval;

/**
 *
 * @author Rick
 */
public class ProfessorService {
    
   private ProfessorJpaController controller;
    
    public ProfessorService(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        controller = new ProfessorJpaController(emf);
    }
    
    
    public List<ProfessorModel> ListarProfessores(){
        
        List<Professor> professores = new LinkedList<Professor>(); 
        List<ProfessorModel> modelos = new LinkedList<ProfessorModel>();
        
        professores = controller.findProfessorEntities();
        
        for (Professor prof : professores){
            ProfessorModel model = new ProfessorModel();
            model.Nome = prof.getNome();
            model.id = prof.getId();
            modelos.add(model);
        }
        
        return modelos;
    }
    
    public List<ProfessorModel> listarProfessoresCompativeisComAusenteNoPeriodo(String nomeProfessor, Interval periodo){
        
        Professor professorAusente = controller.findProfessor(nomeProfessor);
        
        List<Aula> aulasPerdidas = professorAusente.verificarAulasPerdidasNoPeriodo(periodo);
        
        List<Professor> todosProfessores = controller.findProfessorEntities();
        
        List<ProfessorModel> profsPossiveis = new ArrayList<ProfessorModel>();
        
        for(Professor professor : todosProfessores){
            
            if(!professor.equals(professorAusente)){
                //profsMenosAusente.add(professor);
                
                if(professor.EhCompativelCom(aulasPerdidas)){
                    
                    //profsPossiveis.add(professor);
                    
                    ProfessorModel model = new ProfessorModel();
                    
                    model.id = professor.getId();
                    model.Nome = professor.getNome();
                    
                    profsPossiveis.add(model);
                }
            }   
        }
        
        return profsPossiveis;
    }
    
    public ProfessorModel obterProfessorPorNome(String nome){
        
        Professor professor = controller.findProfessor(nome);
        
        if(professor == null)
            return null;
        
        ProfessorModel model = new ProfessorModel();
        
        model.Nome = professor.getNome();
        model.id = professor.getId();
        
        return model;
        
    }
    
    public ProfessorModel obterProfessorPorUsername(String username){
        Professor professor = controller.findProfessorPorUsername(username);
        
        if(professor == null)
            return null;
        
        ProfessorModel model = new ProfessorModel();
        
        model.Nome = professor.getNome();
        model.id = professor.getId();
        
        return model; 
    }
    
    
}
