/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.AusenciaJpaController;
import datamapper.PopulateDB;
import datamapper.ProfessorJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Ausencia;
import dominio.Professor;
import modelo.AusenciaModel;
import servico.NotificacaoService;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import junit.framework.Assert;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.BeforeClass;

/**
 *
 * @author Leticia
 */
public class NotificacaoServiceTest {
    
    private NotificacaoService serviceEmTeste;
    private EntityManagerFactory emf;
    AusenciaJpaController ausController;
    private ProfessorJpaController profController;
    private List<Ausencia> cleanEntity = new LinkedList<Ausencia>();
    
    @Before
    public void setUp(){
        
        //PopulateDB.recreateDB();
        //PopulateDB.populateProfessores();
        emf = Persistence.createEntityManagerFactory("pro_subPU");
        profController = new ProfessorJpaController(emf);
        ausController = new AusenciaJpaController(emf);
        serviceEmTeste = new NotificacaoService();
           
    }
    
    @After
    public void tearDown() throws NonexistentEntityException{
        
        for(Ausencia ausencia : cleanEntity){
            ausController.destroy(ausencia.getId());
        }
        
        cleanEntity = new LinkedList<Ausencia>();
       
        
    }
    
    @BeforeClass
    public static void classSetUp() {
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateProfessores();
    }
    
//    @AfterClass
//    public static void classTearDown() {
//        PopulateDB.recreateDB();
//    }

     @Test
     public void testeDeveSerPossivelNotificarUmaAusencia() throws ParseException {
         
         List<Professor> professores = profController.findProfessorEntities();
         
         String dataInicio = "20/05/2013";
         String dataFim = "24/05/2013";
         String motivo = "Problemas pessoais";
         
         String codigo = serviceEmTeste.notificarAusencia(professores.get(0).getId(), dataInicio, dataFim, motivo, professores.get(1).getId());
         
         
         
         Ausencia ausencia = ausController.findAusencia(codigo);
         
         Assert.assertNotNull(ausencia);
         
         cleanEntity.add(ausencia);

     }
     
     @Test
     public void testeDeveSerPossivelEditarAusencia() throws ParseException, NonexistentEntityException, Exception {
         
         List<Professor> professores = profController.findProfessorEntities();
         
         String dataInicio = "20/05/2013";
         String dataFim = "24/05/2013";
         String motivo = "Problemas pessoais";
         
         String codigo = serviceEmTeste.notificarAusencia(professores.get(0).getId(), dataInicio, dataFim, motivo, professores.get(1).getId());
         
         motivo = "Problemas de força maior.";
             
         
         serviceEmTeste.editarAusencia(codigo, dataInicio, dataFim, motivo, professores.get(1).getId());

         
         Ausencia ausencia = ausController.findAusencia(codigo);
         
         Assert.assertEquals(motivo, ausencia.getMotivo());
         
         cleanEntity.add(ausencia);

     }
     
     @Test
     public void testeDeveSerPossivelListarAusencias() throws ParseException{
         
       
         List<Professor> professores = profController.findProfessorEntities();
         
         String dataInicio = "20/05/2013";
         String dataFim = "24/05/2013";
         String motivo = "Problemas pessoais";
         
         String codigo = serviceEmTeste.notificarAusencia(professores.get(0).getId(), dataInicio, dataFim, motivo, professores.get(1).getId());
         
         dataInicio = "21/05/2013";
         dataFim = "25/05/2013";
         motivo = "Assuntos pessoais";
         
         String codigo2 = serviceEmTeste.notificarAusencia(professores.get(0).getId(), dataInicio, dataFim, motivo, professores.get(1).getId());
         
         dataInicio = "15/05/2013";
         dataFim = "17/05/2013";
         motivo = "Pneumonia";
         
         String codigo3 = serviceEmTeste.notificarAusencia(professores.get(0).getId(), dataInicio, dataFim, motivo, professores.get(1).getId());
         
         List<AusenciaModel> modelos = serviceEmTeste.listarAusencias();
         
         Assert.assertEquals(3, modelos.size());
         
         cleanEntity.add(ausController.findAusencia(codigo));
         cleanEntity.add(ausController.findAusencia(codigo2));
         cleanEntity.add(ausController.findAusencia(codigo3));
     }
     
}