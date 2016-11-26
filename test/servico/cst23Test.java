/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import auxiliar.Perfil;
import datamapper.PopulateDB;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Usuario;
import java.text.ParseException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author prgoes
 */
public class cst23Test {

    public cst23Test() {
    }
    
    @Before
    public void setUp() throws Exception {
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUseCaseTest();
        
    }
    
    @Test
    public void testeEditarUsuario() throws ParseException, Exception {

        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha(
                "Administrador", "123456"));
        AdministracaoDeUsuariosService administracaoDeUsuarioService
                = new AdministracaoDeUsuariosService();
        List<Usuario> usuariosList
                = administracaoDeUsuarioService.listarUsuarios();
        Usuario usuario = usuariosList.get(3);
        assertEquals(usuario.getUsuario(), "Professor3");
        assertEquals(usuario.getSenha(), "123456");
        assertEquals(usuario.getPermissao(), Perfil.PROFESSOR);

        try {
            administracaoDeUsuarioService.editarUsuario(
                    "SenhaEditadaTeste", Perfil.PROFESSOR, usuario.getId());

            Usuario usuarioEditado
                    = administracaoDeUsuarioService.obterUsuario("Professor3");
            assertEquals(usuarioEditado.getUsuario(), "Professor3");
            assertEquals(usuarioEditado.getSenha(), "SenhaEditadaTeste");

            assertTrue(loginService.verificarUsuarioESenha(
                    "Professor3", "SenhaEditadaTeste"));
        } catch (NonexistentEntityException ex) {
            Logger.getLogger(cst23Test.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
