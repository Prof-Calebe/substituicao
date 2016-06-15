/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import datamapper.AulaJpaController;
import datamapper.PopulateDB;
import datamapper.ProfessorJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Aula;
import dominio.Professor;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import modelo.AusenciaModel;
import modelo.ProfessorModel;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;

/**
 *
 * @author prgoes
 */
public class cst06Test {
    
    public cst06Test() {
    }
    
    @BeforeClass
    public static void setUpClass() throws NonexistentEntityException, Exception{
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUseCaseTest();
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        
        DateTime inícioPrimeiroHorário = new DateTime(1900, 01, 01, 18, 30);
        DateTime finalPrimeiroHorário = new DateTime(1900, 01, 01, 20, 00);        
        Interval primeiroHorário = new Interval(inícioPrimeiroHorário, finalPrimeiroHorário);
        
        AulaJpaController aulaJpa = new AulaJpaController(emf);
        Aula aulaProfessor3SegundaFeiraPrimeiroHorário = new Aula("P3 - Segunda 18:30", DateTimeConstants.MONDAY, primeiroHorário);
        aulaJpa.create(aulaProfessor3SegundaFeiraPrimeiroHorário);
        
        ProfessorJpaController profJpa = new ProfessorJpaController(emf);
        Professor professor3 = profJpa.findProfessor("Professor3");
        
        professor3.adicionarAula(aulaProfessor3SegundaFeiraPrimeiroHorário);
        profJpa.edit(professor3);
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws ParseException {
        ProfessorService professorService = new ProfessorService();
        ProfessorModel professor = professorService.obterProfessorPorNome("Professor2");
        
        NotificacaoService notificaçãoService = new NotificacaoService();
        notificaçãoService.notificarAusencia(professor.id, "25/11/2013 18:30", "25/11/2013 19:59", "Motivo Declarado", new LinkedList<String>());
        
        
    }
    
    @After
    public void tearDown() {
    }
    
    @Test
    public void testeEfetuarAlocação_Confirmar() throws ParseException
    {
        LoginService loginService = new LoginService();
        assertTrue(loginService.verificarUsuarioESenha("Administrador", "123456"));
        
        NotificacaoService notificaçãoService = new NotificacaoService();
        List<AusenciaModel> ausencias = notificaçãoService.listarAusencias();        
        assertEquals(1, ausencias.size());
        assertEquals("Professor2", ausencias.get(0).professorAusente);
        assertEquals("", ausencias.get(0).professorSubstituto);
        assertEquals("25/11/2013 18:30", ausencias.get(0).dataInicio);
        assertEquals("25/11/2013 20:00", ausencias.get(0).dataFim);
        assertEquals("Alocação pendente", ausencias.get(0).estado);
        
        ProfessorService professorService = new ProfessorService();
        List<ProfessorModel> professoresCompatíveis = professorService.listarProfessoresCompativeisComAusenteNoPeriodo(ausencias.get(0).id.toString());
        assertEquals(0, professoresCompatíveis.size());       
        
    }

}
