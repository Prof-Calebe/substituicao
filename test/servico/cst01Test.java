/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package servico;

import datamapper.PopulateDB;
import datamapper.exceptions.NonexistentEntityException;
import java.text.ParseException;
import java.util.ArrayList;
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
 * @author prgoes & camposmandy
 */

public class cst01Test {
    
    public cst01Test() {
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
    public void testeNotificaçãoDeAusência_ÚnicoHorário() throws ParseException{
        
        //Login com usuário: Funcionario
        LoginService loginService = new LoginService();
        assertTrue(loginService.VerificarUsuarioESenha("Funcionario", "123456"));
        
        ProfessorService professorService = new ProfessorService();
        ProfessorModel professor = professorService.obterProfessorPorNome("Professor1");
        
        //Funcionalidade de "Notificação de Ausência" 
        NotificacaoService notificaçãoService = new NotificacaoService(); 
        List<AusenciaModel> ausencias = notificaçãoService.listarAusencias();        
        assertEquals(0, ausencias.size());
        
        List<String> professores = new ArrayList();
        professores.add("Professor1");
        
        notificaçãoService.notificarAusencia(professor.id, "13/06/2016 20:05", "13/06/2016 21:35", "Motivo: Palestra", professores);
        
        assertTrue(loginService.VerificarUsuarioESenha("Administrador", "123456"));
        ausencias = notificaçãoService.listarAusenciasPorProfessor(professor.Nome);        
        assertEquals(1, ausencias.size()); //1 notificação pendente
        assertEquals("Professor1", ausencias.get(0).professorAusente);
        assertEquals("", ausencias.get(0).professorSubstituto);
        assertEquals("Alocação pendente", ausencias.get(0).estado);        
    }

}
