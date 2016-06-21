/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import auxiliar.Perfil;
import datamapper.PopulateDB;
import datamapper.exceptions.NonexistentEntityException;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;
import modelo.UsuarioModel;

/**
 *
 * @author prgoes
 */
public class cst23Test {
    
    
    @BeforeClass
    public static void setUpClass(){ 
        //
    }   
    
    @AfterClass
    public static void tearDownClass() {
        //
    }
    
    @Before
    public void setUp() throws Exception {
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUseCaseTest();
        
    }
    
    @After
    public void tearDown() {
        //
    }

    @Test
    public void testeEditarUsuario() throws ParseException, Exception {

        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha(
                "Administrador", "123456"));
        AdministracaoDeUsuariosService administracaoDeUsuarioService
                = new AdministracaoDeUsuariosService();
        List<UsuarioModel> usuariosList
                = administracaoDeUsuarioService.listarUsuarios();
        UsuarioModel usuario = usuariosList.get(3);
        assertEquals(usuario.Usuario, "Professor3");
        assertEquals(usuario.Senha, "123456");
        assertEquals(usuario.profile, Perfil.PROFESSOR);

        try {
            administracaoDeUsuarioService.editarUsuario(
                    "SenhaEditadaTeste", Perfil.PROFESSOR, usuario.id);

            UsuarioModel usuarioEditado
                    = administracaoDeUsuarioService.obterUsuario("Professor3");
            assertEquals(usuarioEditado.Usuario, "Professor3");
            assertEquals(usuarioEditado.Senha, "SenhaEditadaTeste");

            assertTrue(loginService.verificarUsuarioESenha(
                    "Professor3", "SenhaEditadaTeste"));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(cst23Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
