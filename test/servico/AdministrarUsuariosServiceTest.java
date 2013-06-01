/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.PopulateDB;
import datamapper.UsuarioJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Usuario;
import modelo.UsuarioModel;
import servico.AdministrarUsuariosService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Rick
 */
public class AdministrarUsuariosServiceTest {
    
    private AdministrarUsuariosService serviceEmTeste;
    UsuarioJpaController controller;
    EntityManagerFactory emf;
    long idUsuarioJaNoBanco;
    long proximoId;
    
    @Before
    public void setUp() {     
        
        serviceEmTeste = new AdministrarUsuariosService();
        emf = Persistence.createEntityManagerFactory("pro_subPU");
        controller = new UsuarioJpaController(emf);
        idUsuarioJaNoBanco = 1;
        proximoId = controller.getUsuarioCount() + 1;
    }
    
    @BeforeClass
    public static void classSetUp(){
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUsuario();
    }
    
    @AfterClass
    public static void tearDown() {
       PopulateDB.recreateDB("prosub", "root", "");
    }
    
   
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testeDeveSalvarUsuario() throws NonexistentEntityException {
        
        serviceEmTeste.SalvarUsuario("Victor hugo", "senhaDificil", 1);

        Usuario usuarioQueEuColoquei = controller.findUsuario(proximoId);
        
        Assert.assertNotNull(usuarioQueEuColoquei);
        
        controller.destroy(proximoId);
        
    }
    
    @Test
    public void testeEditarUsuario() throws NonexistentEntityException, Exception{
              
        serviceEmTeste.EditarUsuario("novaSenhaDificil", 2, idUsuarioJaNoBanco);
        
        Usuario usuarioModificado = controller.findUsuario(idUsuarioJaNoBanco);
        
        Assert.assertEquals("novaSenhaDificil", usuarioModificado.getSenha());
        Assert.assertEquals(2, usuarioModificado.getPermissao());
        
        
    }
    
    @Test
    public void testeDeveListarTodosOsUsuarios(){
        
        List<UsuarioModel> modelos = serviceEmTeste.ListarUsuarios();
        Assert.assertEquals(3, modelos.size());
        
        
    }
}