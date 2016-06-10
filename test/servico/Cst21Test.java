/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import auxiliar.Perfil;
import datamapper.PopulateDB;
import dominio.Usuario;
import modelo.UsuarioModel;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Victor Messina
 */
public class Cst21Test {


    @BeforeClass
    public static void setUpClass() throws Exception {
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUseCaseTest();
    }

    @Before
    public void setUp() {

        AdministracaoDeUsuariosService aus;

        aus = new AdministracaoDeUsuariosService();

        Usuario usuario1 = new Usuario();

        usuario1.setUsuario("ProfessorTest");
        usuario1.setSenha("123456");
        usuario1.setPermissao(Perfil.PROFESSOR);

        aus.SalvarUsuario(usuario1.getUsuario(), usuario1.getSenha(), usuario1.getPermissao());

    }

    @Test
    public void testeCriarUsuarioJaCriado() {
        LoginService loginService = new LoginService();
        assertTrue(loginService.VerificarUsuarioESenha("Administrador", "123456"));

        AdministracaoDeUsuariosService aus;

        aus = new AdministracaoDeUsuariosService();

        Usuario usuario1 = new Usuario();

        usuario1.setUsuario("ProfessorTest");
        usuario1.setSenha("123456");
        usuario1.setPermissao(Perfil.PROFESSOR);
        
        UsuarioModel usuario2 = aus.obterUsuario(usuario1.getUsuario());

        assertEquals(usuario1.getUsuario(), usuario2.Usuario);
    }
}
