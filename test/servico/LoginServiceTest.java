/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.PopulateDB;
import datamapper.UsuarioJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Usuario;
import servico.LoginService;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import junit.framework.Assert;
import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rick
 */
public class LoginServiceTest {
    
    Usuario user;
    LoginService LgnService;
    List<Usuario> ListaUser;
    UsuarioJpaController UserController;
    
    
    public LoginServiceTest() {
    }
    
    @Before
    public void setUp() {
        
        PopulateDB.recreateDB("prosub", "root", "");
        
        user = new Usuario("Calebe","123456");
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        UserController = new UsuarioJpaController(emf);
        UserController.create(user);
        
        LgnService = new LoginService();
    }
    
    @After
    public void tearDown() {
        try {
            UserController.destroy(user.getId());
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(LoginServiceTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Test
    public void testeValidaUsuarioeSenha(){

        
        Assert.assertTrue(LgnService.verificarUsuarioESenha("Calebe", "123456"));
        Assert.assertFalse(LgnService.verificarUsuarioESenha("Calebe", ""));
        Assert.assertFalse(LgnService.verificarUsuarioESenha("noEcsiste", ""));
        
    }
    
}