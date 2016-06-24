/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import auxiliar.Perfil;

import datamapper.PopulateDB;

import dominio.Usuario;

import java.util.List;

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

        aus.salvarUsuario(usuario1.getUsuario(), usuario1.getSenha(), usuario1.getPermissao());

    }

    @Test
    public void testeCriarUsuarioJaCriado() {
        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha("Administrador", "123456"));

        AdministracaoDeUsuariosService aus;

        aus = new AdministracaoDeUsuariosService();

        Usuario usuario1 = new Usuario();

        usuario1.setUsuario("ProfessorTest");
        usuario1.setSenha("123456");
        usuario1.setPermissao(Perfil.PROFESSOR);

        List<UsuarioModel> usuariosCadastrados;
        usuariosCadastrados = aus.listarUsuarios();

        boolean flag = false;

        for (UsuarioModel um : usuariosCadastrados) {
            if (um.Usuario.equals(usuario1.getUsuario())) {
                flag = true;
            } else {
                aus.salvarUsuario(usuario1.getUsuario(), usuario1.getSenha(), usuario1.getPermissao());

            }
        }

        assertTrue(flag);

    }
}
