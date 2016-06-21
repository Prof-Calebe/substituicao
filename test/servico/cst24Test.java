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
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.*;

import modelo.UsuarioModel;

/*
 *
 * @author Mohamad
 *
 */

public class cst24Test {
    
    @BeforeClass
    public static void setUpClass() throws NonexistentEntityException, Exception{
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUseCaseTest();
    }
    
    //@AfterClass
    //public static void tearDownClass() {
    //}
    
    //@Before
    //public void setUp() throws ParseException, NonexistentEntityException {
    //}
    
    //@After
    //public void tearDown() {
    //}
    
    @Test
    public void testeEdicaoDeUsuarioCancelada() throws ParseException, Exception{
        
        //Login como usuário "Administrador" 
        LoginService loginService = new LoginService();
        assertTrue(loginService.VerificarUsuarioESenha("Administrador", "123456"));
        
        //Navegação até "Editar Usuário" 
        AdministracaoDeUsuariosService editarUs = new AdministracaoDeUsuariosService();
        List<UsuarioModel> usuarios = editarUs.ListarUsuarios(); 
        
        //Seleção e obtenção dos dados do usuário
        UsuarioModel editarUsuario = usuarios.get(1);
        assertEquals(editarUsuario.Usuario,"Professor1");
        assertEquals(editarUsuario.Senha,"123456");
        
        editarUs.EditarUsuario("", Perfil.PROFESSOR, editarUsuario.id);
        
        //Verficação de que os dados não foram alterados
        UsuarioModel usuario = editarUs.obterUsuario("Professor1");
        assertEquals(usuario.Usuario,"Professor1");
        assertEquals(usuario.Senha,"123456");
    }
}