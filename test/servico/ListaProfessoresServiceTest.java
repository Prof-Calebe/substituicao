/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.PopulateDB;
import datamapper.ProfessorJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Professor;
import modelo.ProfessorModel;
import servico.ListaProfessoresService;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rick
 */
public class ListaProfessoresServiceTest {
    
    private ListaProfessoresService serviceEmTeste;
    
    @Before
    public void setUp() throws NonexistentEntityException, Exception {
        
        PopulateDB.fullSetupDB("prosub", "root", "");
        serviceEmTeste = new ListaProfessoresService();
        
    }
    
    @After
    public void tearDown() {
    }

    
    @Test
    public void testeDeveListarTodosOsProfessores() {
        
        List<ProfessorModel> modelos = serviceEmTeste.ListarProfessores();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        ProfessorJpaController controller = new ProfessorJpaController(emf);
        
        List<Professor> professores = controller.findProfessorEntities();
        
        Assert.assertEquals(professores.size(), modelos.size());
        Assert.assertEquals("Calebe", modelos.get(0).Nome);
        Assert.assertEquals("Ana Claudia", modelos.get(1).Nome);
    }
    
    @Test
    public void testeDeveAcharProfessorPorNome(){
        
        ProfessorModel modelo = serviceEmTeste.obterProfessor("ariovaldsssonnnn");
        
        Assert.assertNull(modelo);
        
        modelo = serviceEmTeste.obterProfessor("Calebe");
        
        Assert.assertNotNull(modelo);
        
    }
}