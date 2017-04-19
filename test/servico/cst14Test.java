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
import modelo.AusenciaModel;
import modelo.ProfessorModel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author GUSTAVO
 */
public class cst14Test {

    public cst14Test() {

    }

    @BeforeClass
    public static void setUpClass() throws NonexistentEntityException, Exception {
        PopulateDB.recreateDB("prosub", "root", "");
        PopulateDB.populateUseCaseTest();
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() throws ParseException, NonexistentEntityException {
        //Declaração de Ausência do Professor1 na Segunda, horário 20:00 - 23:00
        ProfessorService professorService = new ProfessorService();
        ProfessorModel professor = professorService.obterProfessorPorNome("Professor1");
        NotificacaoService notificaçãoService = new NotificacaoService();
        notificaçãoService.notificarAusencia(professor.id, "06/06/2016 20:00", "06/06/2016 23:00", "", new LinkedList<String>());

    }

    @After
    public void tearDown() {
    }

    @Test
    public void testeCancelarAusenciaConfirma() {
        LoginService loginService = new LoginService();
        assertTrue(loginService.VerificarUsuarioESenha("Professor1", "3221"));

        NotificacaoService notificaçãoService = new NotificacaoService();
        List<AusenciaModel> ausencias = notificaçãoService.listarAusenciasPorProfessor("Professor1");
        assertEquals(1, ausencias.size());
        assertEquals("Professor1", ausencias.get(0).professorAusente);
        assertEquals("25/11/2013 18:30", ausencias.get(0).dataInicio);
        assertEquals("25/11/2013 20:00", ausencias.get(0).dataFim);
        notificaçãoService.cancelarAusencia("0");
        notificaçãoService.recusarSubstituicao(ausencias.get(0).id);

        ausencias = notificaçãoService.listarAusencias();
        assertEquals(0, ausencias.size());
        
        
        
    }
}
