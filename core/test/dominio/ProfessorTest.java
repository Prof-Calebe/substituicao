/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.easymock.EasyMock;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leticia
 */
public class ProfessorTest {
    
    //Dá pra mockar classe Aula
    
    private Professor objetoEmTeste;
    private Interval periodo;
    private String nome;
    private String username;
    private List<Aula> aulas;
    
    private Interval p1;
    private Interval p2;
    private Interval p3;
    private Interval p4;
    private Interval p5;
    private Interval p6;
    private Interval p7;
    private Interval p8;
    
    private Aula aula1;
    private Aula aula2;
    private Aula aula3;
    private Aula aula4;
    private Aula aula5;
    private Aula aula6;
    private Aula aula7;
    private Aula aula8;
    
    public ProfessorTest() {
    }
    
    @Before
    public void setUp() {
        
        nome = "ariovaldson";
        username = "ari";
            
        objetoEmTeste = new Professor(nome, username);
        
        DateTime limiteInfP1 = new DateTime(2013, 05, 20, 22, 40);
        DateTime limiteSupP1 = new DateTime(2013, 05, 20, 23, 59);
        
        
        p1 = new Interval(limiteInfP1, limiteSupP1);
        
        DateTime limiteInfP2 = new DateTime(2013, 05, 20, 20, 40);
        DateTime limiteSupP2 = new DateTime(2013, 05, 20, 21, 40);
        
        p2 = new Interval(limiteInfP2, limiteSupP2);
        
        DateTime limiteInfP3 = new DateTime(2013, 05, 20, 19, 45);
        DateTime limiteSupP3 = new DateTime(2013, 05, 20, 20, 59);
                
        p3 = new Interval(limiteInfP3, limiteSupP3);
        
        DateTime limiteInfP4 = new DateTime(2013, 05, 20, 19, 15);
        DateTime limiteSupP4 = new DateTime(2013, 05, 20, 20, 00);
                      
        p4 = new Interval(limiteInfP4, limiteSupP4);
        
        DateTime limiteInfP5 = new DateTime(2013, 05, 20, 19, 25);
        DateTime limiteSupP5 = new DateTime(2013, 05, 20, 20, 30);
        
        p5 = new Interval(limiteInfP5, limiteSupP5);
        
        DateTime limiteInfP6 = new DateTime(2013, 05, 20, 22, 50);
        DateTime limiteSupP6 = new DateTime(2013, 05, 20, 23, 30);
        
        p6 = new Interval(limiteInfP6, limiteSupP6);
       
        
        DateTime limiteInfP7 = new DateTime(2013, 05, 20, 10, 10);
        DateTime limiteSupP7 = new DateTime(2013, 05, 20, 10, 30);
        
        p7 = new Interval(limiteInfP7, limiteSupP7);
        
        DateTime limiteInfP8 = new DateTime(2013, 05, 21, 10, 10);
        DateTime limiteSupP8 = new DateTime(2013, 05, 21, 18, 10);
        
        Interval p8 = new Interval(limiteInfP8, limiteSupP8);
        
        aula1 = new Aula(Calendar.MONDAY, p1);
        aula2 = new Aula(Calendar.TUESDAY, p2);
        aula3 = new Aula(Calendar.WEDNESDAY, p3);
        aula4 = new Aula(Calendar.THURSDAY, p4);
        aula5 = new Aula(Calendar.THURSDAY, p5);
        aula6 = new Aula(Calendar.MONDAY, p6);
        aula7 = new Aula(Calendar.SATURDAY, p7);
        
        aulas = new LinkedList<Aula>();
        
        aulas.add(aula1);
        aulas.add(aula2);
        aulas.add(aula3);
        aulas.add(aula4);
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    
    @Test
    public void testeDeveSerInicializadoComNome() {

        Assert.assertEquals(objetoEmTeste.getNome(), nome);
        Assert.assertEquals(objetoEmTeste.getUsername(), username);
    }

    
    @Test
    public void testeDeveSerPossivelAdicionarUmaAulaNaGradeDoProfessor(){
        objetoEmTeste.adicionarAula(aula1);
        
        Assert.assertEquals(objetoEmTeste.getGrade().size(), 1);
        Assert.assertEquals(objetoEmTeste.getGrade().toArray()[0], aula1);
    }
    
    @Test 
    public void testeDeveVerificarCompatibilidadeEntreProfessorEAulas(){
        
        objetoEmTeste.adicionarAula(aula5);
        
        //Collections.sort(aulas, new AulaComparator());

        Assert.assertFalse(objetoEmTeste.EhCompativelCom(aulas));
        
        objetoEmTeste = new Professor("outroNome", "username");
        
        objetoEmTeste.adicionarAula(aula6);
        
        Assert.assertFalse(objetoEmTeste.EhCompativelCom(aulas));
        
        objetoEmTeste = new Professor("outroNomeAinda", "username");
        
        objetoEmTeste.adicionarAula(aula7);
        
        Assert.assertTrue(objetoEmTeste.EhCompativelCom(aulas));
    }
    
    @Test
    public void testeDeveDevolverAulasQuePerdeNoPeriodo(){
        
        DateTime inicio1 = new DateTime(2013, 05, 24, 10, 10);
        DateTime fim1 = new DateTime(2013, 05, 26, 10, 30);
        
        Interval ausencia1 = new Interval(inicio1, fim1);
        
        DateTime inicioAula1 = new DateTime(2013, 05, 24, 19, 20);
        DateTime fimAula1 = new DateTime(2013, 05, 24, 21, 0);
        
        Interval intervalAula1 = new Interval(inicioAula1, fimAula1);
        
        Aula aula1 = new Aula(DateTimeConstants.TUESDAY, intervalAula1);
        
        objetoEmTeste = new Professor("umProf", "username");
        objetoEmTeste.adicionarAula(aula1);
        
        List<Aula> aulasPerdidas = objetoEmTeste.verificarAulasPerdidasNoPeriodo(ausencia1);
        
        Assert.assertEquals(0, aulasPerdidas.size());
        
        DateTime inicioAula2 = new DateTime(2013, 05, 24, 05, 0);
        DateTime fimAula2 = new DateTime(2013, 05, 24, 07, 0);
        
        Interval intervalAula2 = new Interval(inicioAula2, fimAula2);
        
        Aula aula2 = new Aula(DateTimeConstants.FRIDAY, intervalAula2);     
        
        objetoEmTeste = new Professor("outroProf", "username");
        objetoEmTeste.adicionarAula(aula2);
        
        aulasPerdidas = objetoEmTeste.verificarAulasPerdidasNoPeriodo(ausencia1);
        
        Assert.assertEquals(1, aulasPerdidas.size());
        Assert.assertEquals(aula2, aulasPerdidas.toArray()[0]);
        
        
        
        DateTime inicio2 = new DateTime(2013, 05, 29, 10, 00);
        DateTime fim2 = new DateTime(2013, 05, 29, 14, 00);
        
        Interval ausencia2 = new Interval(inicio2, fim2);
        
        DateTime inicioAula3 = new DateTime(2013, 05, 24, 10, 0);
        DateTime fimAula3 = new DateTime(2013, 05, 24, 11, 0);
        
        Interval intervalAula3 = new Interval(inicioAula3, fimAula3);
        
        Aula aula3 = new Aula(DateTimeConstants.WEDNESDAY, intervalAula3);      
        
        objetoEmTeste = new Professor("outroProf", "username");
        objetoEmTeste.adicionarAula(aula3);
        
        aulasPerdidas = objetoEmTeste.verificarAulasPerdidasNoPeriodo(ausencia2);
        
        Assert.assertEquals(1, aulasPerdidas.size());
        Assert.assertEquals(aula3, aulasPerdidas.toArray()[0]);

        DateTime inicioAula4 = new DateTime(2013, 05, 24, 15, 0);
        DateTime fimAula4 = new DateTime(2013, 05, 24, 17, 0);
        
        Interval intervalAula4 = new Interval(inicioAula4, fimAula4);
        
        Aula aula4 = new Aula(DateTimeConstants.WEDNESDAY, intervalAula4);      
        
        objetoEmTeste = new Professor("outroProf", "username");
        objetoEmTeste.adicionarAula(aula4);
        
        aulasPerdidas = objetoEmTeste.verificarAulasPerdidasNoPeriodo(ausencia2);
        
        Assert.assertEquals(0, aulasPerdidas.size());        
        
    }
}