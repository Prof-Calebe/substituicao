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

import org.junit.BeforeClass;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 * @author Victor Messina
 */
public class Cst20Test {

    @BeforeClass
    public static void setUpClass() throws Exception {

        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUseCaseTest();
    }

    @Test
    public void testeCriarUsuario() {
        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha("Administrador", "123456"));

        AdministracaoDeUsuariosService aus;

        aus = new AdministracaoDeUsuariosService();

        Usuario usuario1 = new Usuario();

        usuario1.setUsuario("ProfessorTest");
        usuario1.setSenha("123456");
        usuario1.setPermissao(Perfil.PROFESSOR);

        aus.salvarUsuario(usuario1.getUsuario(), usuario1.getSenha(), usuario1.getPermissao());

        UsuarioModel usuario2 = aus.obterUsuario(usuario1.getUsuario());

        boolean flag = false;

        if (usuario2 != null) {

            flag = true;

            assertTrue("Usuário criado com sucesso", flag);
            
        }else{
            
            assertFalse("O usuário não foi criado com sucesso", flag);
            
        }
    }
}