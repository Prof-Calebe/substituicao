/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import datamapper.PopulateDB;
import datamapper.exceptions.NonexistentEntityException;
import java.security.InvalidParameterException;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import modelo.AusenciaModel;
import modelo.ProfessorModel;

/**
 *
 * @author prgoes e mohamad
 */

public class cst03Test {
    
    public cst03Test() {
    }
    
    @BeforeClass
    public static void setUpClass() throws NonexistentEntityException, Exception{
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
    public void testeNotificaçãoDeAusênciaSemMotivo() throws ParseException{
        //Login com usuário "Funcionario"
        LoginService loginService = new LoginService();
        assertTrue(loginService.VerificarUsuarioESenha("Funcionario1", "123456"));
        
        ProfessorService professorService = new ProfessorService();
        ProfessorModel professor = professorService.obterProfessorPorNome("Professor1");
        
        //Navegação de "Notificação de ausência" 
        NotificacaoService notificaçãoService = new NotificacaoService();
        List<AusenciaModel> ausencias = notificaçãoService.listarAusencias();        
        assertEquals(0, ausencias.size());
        
        Boolean exceptionOk = false;
        
        try{
            //notifica ausência do Professor
            notificaçãoService.notificarAusencia(professor.id, "13/06/2016 20:01", "13/06/2016 21:29", "", new LinkedList<String>());
        }
        catch(InvalidParameterException ex)
        {
            exceptionOk = true;
        }
        
        //alteração de usuário logado
        assertTrue(loginService.VerificarUsuarioESenha("Administrador", "123456"));
        
        //navegação e verificação de notificações pendentes
        ausencias = notificaçãoService.listarAusencias();        
        assertEquals(0, ausencias.size());
        assertTrue(exceptionOk);
    }

}
