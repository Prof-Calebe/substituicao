/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import datamapper.PopulateDB;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Ausencia;
import dominio.Professor;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 *
 * @author prgoes
 */
public class cst01Test {
    
    public cst01Test() {
    }
    
    @BeforeClass
    public static void setUpClass() 
            throws NonexistentEntityException, Exception{
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
    public void testeNotificacaoDeAusenciaUnicoHorário() 
            throws ParseException
    {
        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha("Funcionario1", "123456"));
        
        ProfessorService professorService = new ProfessorService();
        Professor professor = 
                professorService.obterProfessorPorNome("Professor1");
        
        NotificacaoService notificaçãoService = new NotificacaoService();
        List<Ausencia> ausencias = notificaçãoService.listarAusencias();        
        assertEquals(0, ausencias.size());
        
        notificaçãoService.notificarAusencia(professor.getId(), "25/11/2013 20:01",
                "25/11/2013 21:29", "Motivo Declarado", new LinkedList<String>());
        
        assertTrue(loginService.verificarUsuarioESenha("Administrador", "123456"));
        ausencias = notificaçãoService.listarAusencias();        
        assertEquals(1, ausencias.size());
        assertEquals("Professor1", ausencias.get(0).getProfessor().getNome());
        assertEquals("", ausencias.get(0).getProfessorSubstituto().getNome());
        assertEquals("Alocação pendente", ausencias.get(0).getEstado());        
    }

}
