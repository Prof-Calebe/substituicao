/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.PopulateDB;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Aula;
import dominio.Ausencia;
import dominio.Professor;
import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;
import modelo.AusenciaModel;
import modelo.ProfessorModel;
import org.joda.time.Interval;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 *
 * @author GUSTAVO
 */
public class Cst12Test {

    @BeforeClass
    public static void setUpClass() throws Exception {
        PopulateDB.recreateDB("prosub", "root", "");

            PopulateDB.populateUseCaseTest();
    }

    @Before
    public void setUp() throws ParseException, NonexistentEntityException {
        //Declaração do Professor 1 como ausente
        ProfessorService professorService = new ProfessorService();
        ProfessorModel professor = professorService.obterProfessorPorNome("Professor1");

        NotificacaoService notificaçãoService = new NotificacaoService();
        notificaçãoService.notificarAusencia(professor.id, "06/06/2016 20:00", "06/06/2016 23:00", 
                "Sem motivo", new LinkedList<String>());

        //Declaração do Professor 2 como o substituto
        List<AusenciaModel> ausencias = notificaçãoService.listarAusencias();
        notificaçãoService.definirSubstituto(ausencias.get(0).codigo, "Professor2");
    }

    @Test
    public void testeRejeitarAlocacaoConfirmar() {
        LoginService loginService = new LoginService();
        assertTrue(loginService.VerificarUsuarioESenha("Professor2", "123"));
        Professor professor = new Professor("José Carlos Silva", "Zeca");
        NotificacaoService notificaçãoService = new NotificacaoService();
        List<AusenciaModel> ausencias = notificaçãoService.listarAusenciasPorSubstituto("Professor2");
        Interval interval = new Interval(20, 23);
        Aula aula = new Aula("Sistemas", 1, interval);
        Ausencia au = new Ausencia("1", interval, professor, "Falta", aula);
        au.setMotivo("Consulta médica agendada dentro do prazo estabelecido no regulamento");
        assertEquals(1, ausencias.size());
        assertEquals("Professor1", ausencias.get(0).professorAusente);
        assertEquals("Professor2", ausencias.get(0).professorSubstituto);
        assertEquals("06/06/2016 20:00", ausencias.get(0).dataInicio);
        assertEquals("06/06/2016 23:00", ausencias.get(0).dataFim);
        assertEquals("Alocação efetuada", ausencias.get(0).estado);
        notificaçãoService.recusarSubstituicao(ausencias.get(0).id);//Recusar Alocação
        ausencias = notificaçãoService.listarAusencias();
        assertEquals(1, ausencias.size());
        assertEquals("Professor1", ausencias.get(0).professorAusente);
        assertEquals("Professor2", ausencias.get(0).professorSubstituto);
        assertEquals("06/06/2016 20:00", ausencias.get(0).dataInicio);
        assertEquals("06/06/2016 23:00", ausencias.get(0).dataFim);
        assertEquals("Alocação pendente", ausencias.get(0).estado);
    }
}