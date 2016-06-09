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
import modelo.UsuarioModel;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Victor Messina
 */
public class cst20Test {

    public cst20Test() {
    }

    @BeforeClass
    public static void setUpClass() throws NonexistentEntityException, Exception {
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUseCaseTest();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testeCriarUsuario() {
        LoginService loginService = new LoginService();
        assertTrue(loginService.VerificarUsuarioESenha("Admin", "123456"));

        AdministracaoDeUsuariosService aus = new AdministracaoDeUsuariosService();
        Usuario usuario1 = new Usuario();

        usuario1.setUsuario("ProfessorTest");
        usuario1.setSenha("123456");
        usuario1.setPermissao(Perfil.PROFESSOR);

        assertNull(aus.obterUsuario(usuario1.getUsuario()));

        aus.SalvarUsuario(usuario1.getUsuario(), usuario1.getSenha(), usuario1.getPermissao());

        UsuarioModel professor2 = aus.obterUsuario(usuario1.getUsuario());

        assertEquals(usuario1, professor2);
    }

}
