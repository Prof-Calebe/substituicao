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
import junit.framework.Assert;
import modelo.AusenciaModel;
import modelo.ProfessorModel;
import modelo.UsuarioModel;
import org.junit.After;
import org.junit.AfterClass;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 *
 * @author GUSTAVO
 */
public class cst16Test {

    public cst16Test() {
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
    public void setUp() throws ParseException {
        ProfessorService professorService = new ProfessorService();
        ProfessorModel professor = professorService.obterProfessorPorNome("Professor1");
        
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testeCancelarAulasConfirma() {
        LoginService loginService = new LoginService();
        assertTrue(loginService.VerificarUsuarioESenha("Administrador", "1234"));

        ProfessorService prof = new ProfessorService();
        List<ProfessorModel> aulas = prof.ListarProfessores();
        Assert.assertEquals("Professor1", aulas.get(0).Nome);
        Assert.assertEquals(3, aulas.size());
    }
}
