/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import datamapper.PopulateDB;
import datamapper.exceptions.NonexistentEntityException;
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
import servico.LoginService;
import servico.NotificacaoService;
import servico.ProfessorService;

/**
 *
 * @author prgoes
 */
public class cst02Test {
    
    public cst02Test() {
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
    public void testeNotificaçãoDeAusência_DiaInteiro() throws ParseException
    {
        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha("Funcionario1", "123456"));
        
        ProfessorService professorService = new ProfessorService();
        ProfessorModel professor = professorService.obterProfessorPorNome("Professor1");
        
        NotificacaoService notificaçãoService = new NotificacaoService();
        List<AusenciaModel> ausencias = notificaçãoService.listarAusencias();        
        assertEquals(0, ausencias.size());
        
        notificaçãoService.notificarAusencia(professor.id, "25/11/2013", "25/11/2013", "Motivo Declarado", new LinkedList<String>());
        
        assertTrue(loginService.verificarUsuarioESenha("Administrador", "123456"));
        ausencias = notificaçãoService.listarAusencias();        
        assertEquals(3, ausencias.size());
        assertEquals("Professor1", ausencias.get(0).professorAusente);
        assertEquals("", ausencias.get(0).professorSubstituto);
        assertEquals("Alocação pendente", ausencias.get(0).estado);
        assertEquals("Professor1", ausencias.get(1).professorAusente);
        assertEquals("", ausencias.get(1).professorSubstituto);
        assertEquals("Alocação pendente", ausencias.get(1).estado);
        assertEquals("Professor1", ausencias.get(2).professorAusente);
        assertEquals("", ausencias.get(2).professorSubstituto);
        assertEquals("Alocação pendente", ausencias.get(2).estado);
        
    }
}
