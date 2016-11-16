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

import dominio.Ausencia;
import dominio.Professor;

/**
 *
 * @author prgoes
 */
public class cst10Test {
    
    public cst10Test() {
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
    public void setUp() throws ParseException, NonexistentEntityException {
        //Setup Declaração de Ausência Professor 2
        ProfessorService professorService = new ProfessorService();
        Professor professor =
                professorService.obterProfessorPorNome("Professor2");
        
        NotificacaoService notificaçãoService = new NotificacaoService();
        notificaçãoService.notificarAusencia(
                professor.getId(), "25/11/2013 18:30", "25/11/2013 19:59", 
                "Motivo Declarado", new LinkedList<String>());
        
        //Setup Professor 3 como substituto
        List<Ausencia> ausencias = notificaçãoService.listarAusencias();    
        notificaçãoService.definirSubstituto(ausencias.get(0).getCodigo(),"Professor3");
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testeAceitarAlocação() throws ParseException
    {
        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha("Professor3", "123456"));
        
        NotificacaoService notificaçãoService = new NotificacaoService();
        List<Ausencia> ausencias = 
                notificaçãoService.listarAusenciasPorSubstituto("Professor3");
        assertEquals(1, ausencias.size());
        assertEquals("Professor2", ausencias.get(0).getProfessor().getNome());
        assertEquals("Professor3", ausencias.get(0).getProfessorSubstituto().getNome());
        assertEquals("25/11/2013 18:30", ausencias.get(0).getPeriodo().getStart());
        assertEquals("25/11/2013 20:00", ausencias.get(0).getPeriodo().getEnd());
        assertEquals("Alocação efetuada", ausencias.get(0).getEstado().getDescricao()); 
        
        notificaçãoService.aceitarSubstituicao(ausencias.get(0).getId());
        
        ausencias = 
                notificaçãoService.listarAusenciasPorSubstituto("Professor3");
        assertEquals(1, ausencias.size());
        assertEquals("Professor2", ausencias.get(0).getProfessor().getNome());
        assertEquals("Professor3", ausencias.get(0).getProfessorSubstituto().getNome());
        assertEquals("25/11/2013 18:30", ausencias.get(0).getPeriodo().getStart());
        assertEquals("25/11/2013 20:00", ausencias.get(0).getPeriodo().getEnd());
        assertEquals("Alocação confirmada", ausencias.get(0).getEstado().getDescricao());         
    }

}
