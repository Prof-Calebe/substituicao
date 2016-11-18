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
import org.joda.time.DateTime;

/**
 *
 * @author prgoes
 */
public class cst04Test {
    
    public cst04Test() {
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
    public void setUp() throws ParseException {
        ProfessorService professorService = new ProfessorService();
        Professor professor = professorService.obterProfessorPorNome("Professor2");
        
        NotificacaoService notificaçãoService = new NotificacaoService();
        notificaçãoService.notificarAusencia(professor.getId(), "25/11/2013 18:30", "25/11/2013 19:59", "Motivo Declarado", new LinkedList<String>());
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testeEfetuarAlocação_Confirmar() throws ParseException, NonexistentEntityException
    {
        DateTime inicio = new DateTime(2013, 11, 25, 18, 30);
        DateTime fim = new DateTime(2013, 11, 25, 19, 59);
        
        
        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha("Administrador", "123456"));
        
        
        
        NotificacaoService notificaçãoService = new NotificacaoService();
        List<Ausencia> ausencias = notificaçãoService.listarAusencias();        
        assertEquals(1, ausencias.size());
        assertEquals("Professor2", ausencias.get(0).getProfessor().getNome());
        assertEquals("", ausencias.get(0).getProfessorSubstituto().getNome());
        assertEquals(inicio.toDate(),ausencias.get(0).getPeriodo().getStart().toDate());
        assertEquals(fim.toDate(),ausencias.get(0).getPeriodo().getEnd().toDate());
        assertEquals("Alocação pendente", ausencias.get(0).getEstado().getDescricao());
        
        
        
        
        ProfessorService professorService = new ProfessorService();
        List<Professor> professoresCompatíveis = professorService.listarProfessoresCompativeisComAusenteNoPeriodo(ausencias.get(0).getId().toString());
        assertEquals(1, professoresCompatíveis.size());
        assertEquals("Professor3", professoresCompatíveis.get(0).getNome());
        
        notificaçãoService.definirSubstituto(ausencias.get(0).getCodigo(),"Professor3");
        
        ausencias = notificaçãoService.listarAusencias();        
        assertEquals(1, ausencias.size());
        assertEquals("Professor2", ausencias.get(0).getProfessor().getNome());
        assertEquals("Professor3", ausencias.get(0).getProfessorSubstituto().getNome());
        assertEquals(inicio.toDate(),ausencias.get(0).getPeriodo().getStart().toDate());
        assertEquals(fim.toDate(),ausencias.get(0).getPeriodo().getEnd().toDate());
        assertEquals("Alocação efetuada", ausencias.get(0).getEstado().getDescricao());
    }

}
