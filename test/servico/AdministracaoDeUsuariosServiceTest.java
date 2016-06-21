/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import auxiliar.Perfil;
import datamapper.PopulateDB;
import datamapper.UsuarioJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Usuario;
import modelo.UsuarioModel;
import servico.AdministracaoDeUsuariosService;
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
public class AdministracaoDeUsuariosServiceTest {
    
    private AdministracaoDeUsuariosService serviceEmTeste;
    UsuarioJpaController controller;
    EntityManagerFactory emf;
    long idUsuarioJaNoBanco;
    long proximoId;
    
    @Before
    public void setUp() {     
        
        serviceEmTeste = new AdministracaoDeUsuariosService();
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
    
    @Test
    public void testeDeveSalvarUsuario() throws NonexistentEntityException {
        
        serviceEmTeste.salvarUsuario("Victor hugo", "senhaDificil",
                Perfil.FUNCIONARIO);

        Usuario usuarioQueEuColoquei = controller.findUsuario(proximoId);
        
        Assert.assertNotNull(usuarioQueEuColoquei);
        
        controller.destroy(proximoId);
        
    }
    
    @Test
    public void testeEditarUsuario() throws NonexistentEntityException,
            Exception{
              
        serviceEmTeste.editarUsuario("novaSenhaDificil", Perfil.PROFESSOR,
                idUsuarioJaNoBanco);
        
        Usuario usuarioModificado = controller.findUsuario(idUsuarioJaNoBanco);
        
        Assert.assertEquals("novaSenhaDificil", usuarioModificado.getSenha());
        Assert.assertEquals(Perfil.PROFESSOR, usuarioModificado.getPermissao());
        
        
    }
    
    @Test
    public void testeDeveListarTodosOsUsuarios(){
        
        int countUsuarios = controller.getUsuarioCount();
        
        List<UsuarioModel> modelos = serviceEmTeste.listarUsuarios();
        Assert.assertEquals(countUsuarios, modelos.size());
    }
    
    @Test
    public void testeEncontrarUsuarioPorNome(){
        
        UsuarioModel modelo = serviceEmTeste.obterUsuario("calebe");
        
        assertNotNull(modelo);
    }
}