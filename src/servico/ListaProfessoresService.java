/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.ProfessorJpaController;
import datamapper.UsuarioJpaController;
import dominio.Professor;
import modelo.ProfessorModel;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rick
 */
public class ListaProfessoresService {
    
   private ProfessorJpaController controller;
    
    public ListaProfessoresService(){
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
}
