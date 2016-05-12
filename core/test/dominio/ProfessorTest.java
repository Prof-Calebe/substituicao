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
        
        aula1 = new Aula("Aula1", Calendar.MONDAY, p1);
        aula2 = new Aula("Aula2", Calendar.TUESDAY, p2);
        aula3 = new Aula("Aula3", Calendar.WEDNESDAY, p3);
        aula4 = new Aula("Aula4", Calendar.THURSDAY, p4);
        aula5 = new Aula("Aula5", Calendar.THURSDAY, p5);
        aula6 = new Aula("Aula6", Calendar.MONDAY, p6);
        aula7 = new Aula("Aula7", Calendar.SATURDAY, p7);
        
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
   
    private Aula setUpAulaSextaFeira() {
        DateTime inicioAula2 = new DateTime(2013, 05, 24, 05, 0);
        DateTime fimAula2 = new DateTime(2013, 05, 24, 07, 0);
        Interval intervalAula2 = new Interval(inicioAula2, fimAula2);
        Aula aula2 = new Aula("Aula2", DateTimeConstants.FRIDAY, intervalAula2);
        objetoEmTeste = new Professor("outroProf", "username");
        objetoEmTeste.adicionarAula(aula2);
        return aula2;
    }

    private void setUpAulaTerçaFeira() {
        DateTime inicioAula1 = new DateTime(2013, 05, 24, 19, 20);
        DateTime fimAula1 = new DateTime(2013, 05, 24, 21, 0);
        
        Interval intervalAula1 = new Interval(inicioAula1, fimAula1);
        
        Aula aula1 = new Aula("Aula1", DateTimeConstants.TUESDAY, intervalAula1);
        
        objetoEmTeste = new Professor("umProf", "username");
        objetoEmTeste.adicionarAula(aula1);
    }

    private Interval setUpAusenciaSextaESábado() {
        DateTime inicio1 = new DateTime(2013, 05, 24, 10, 10);
        DateTime fim1 = new DateTime(2013, 05, 26, 10, 30);
        Interval ausencia1 = new Interval(inicio1, fim1);
        return ausencia1;
    }
    
    @Test
    public void testeDeveReportarCorretamenteToString(){
        Long x = new Long("0");    
        objetoEmTeste.setId(x);
        
        assertEquals("Dominio.Professor[ id=0 ]", objetoEmTeste.toString());
        
        x = new Long("10");    
        objetoEmTeste.setId(x);
        assertEquals("Dominio.Professor[ id=10 ]", objetoEmTeste.toString());        
    }
    
    @Test
    public void testeDeveSerConsideradoIgualSomenteAOutroProfessorDeIdIdentica()
    {
        Long x = new Long("0");  
        assertFalse(objetoEmTeste.equals(x));           
        
        Professor outro = new Professor(nome, username);
        assertTrue(objetoEmTeste.equals(outro));  
        
        outro.setId(x);
        assertFalse(objetoEmTeste.equals(outro));      
                
        objetoEmTeste.setId(x);
        assertTrue(objetoEmTeste.equals(outro));  
        
        outro = new Professor(nome, username);        
        assertFalse(objetoEmTeste.equals(outro));       
    }
    
    @Test
    public void testeDeveReportarCorretamenteOHashCode(){
        int hashCode = 0;
        assertEquals(hashCode, objetoEmTeste.hashCode());
        
        Long x = new Long("0");  
        objetoEmTeste.setId(x);
        
        hashCode = x.hashCode();
        assertEquals(hashCode, objetoEmTeste.hashCode());
    }
    
    @Test(expected=IllegalStateException.class)
    public void testeNãoDevePermitirAdicionarAulasNulas(){
        objetoEmTeste.adicionarAula(null);
    }
    
    @Test(expected=IllegalStateException.class)
    public void testeNãoDevePermitirAdicionarAulasDuplicadas(){
        DateTime inicioAula1 = new DateTime(2013, 05, 24, 19, 20);
        DateTime fimAula1 = new DateTime(2013, 05, 24, 21, 0);
        
        Interval intervalAula1 = new Interval(inicioAula1, fimAula1);
        
        Aula aula1 = new Aula("Aula1", DateTimeConstants.TUESDAY, intervalAula1);
        
        objetoEmTeste.adicionarAula(aula1);
        
        objetoEmTeste.adicionarAula(aula1);
    }
    
    @Test
    public void testeDeveGerarAusenciasParaAsAulasExistentesEmUmDeterminadoPeríodo()
    {
        DateTime inícioPrimeiroHorário = new DateTime(1900, 01, 01, 18, 30);
        DateTime finalPrimeiroHorário = new DateTime(1900, 01, 01, 20, 00);                      
        Interval primeiroHorário = new Interval(inícioPrimeiroHorário, finalPrimeiroHorário);
        
        DateTime inícioSegundoHorário = new DateTime(1900, 01, 01, 20, 00);
        DateTime finalSegundoHorário = new DateTime(1900, 01, 01, 21, 30);                      
        Interval segundoHorário = new Interval(inícioSegundoHorário, finalSegundoHorário);
        
        Aula segundaFeiraPrimeiroHorário = new Aula("Aula1", org.joda.time.DateTimeConstants.MONDAY, primeiroHorário);
        segundaFeiraPrimeiroHorário.setId(new Long("1"));        
        
        Aula segundaFeiraSegundoHorário = new Aula("Aula2", org.joda.time.DateTimeConstants.MONDAY, segundoHorário);
        segundaFeiraSegundoHorário.setId(new Long("2"));
        
        Aula terçaFeiraPrimeiroHorário = new Aula("Aula3", org.joda.time.DateTimeConstants.TUESDAY, primeiroHorário);
        terçaFeiraPrimeiroHorário.setId(new Long("3"));
        
        Aula quartaFeiraSegundoHorário = new Aula("Aula4", org.joda.time.DateTimeConstants.WEDNESDAY, segundoHorário);
        quartaFeiraSegundoHorário.setId(new Long("4"));
        
        objetoEmTeste.adicionarAula(segundaFeiraPrimeiroHorário);
        objetoEmTeste.adicionarAula(segundaFeiraSegundoHorário);
        objetoEmTeste.adicionarAula(quartaFeiraSegundoHorário);
        
        DateTime inícioAusência = new DateTime(2013,11,18,00,00);
        DateTime finalAusência = new DateTime(2013,11,20,23,59);
        Interval períodoDeAusência = new Interval(inícioAusência, finalAusência);
        
        List<Ausencia> ausências = objetoEmTeste.gerarAusencias(períodoDeAusência,"sem saco");
        
        assertEquals(3, ausências.size());
        
        inícioAusência = new DateTime(2013,11,18,00,00);
        finalAusência = new DateTime(2013,11,26,23,59);
        períodoDeAusência = new Interval(inícioAusência, finalAusência);
        
        ausências = objetoEmTeste.gerarAusencias(períodoDeAusência,"sem saco");
        assertEquals(5, ausências.size());
        
        inícioAusência = new DateTime(2013,11,18,17,00);
        finalAusência = new DateTime(2013,11,18,19,00);
        períodoDeAusência = new Interval(inícioAusência, finalAusência);
        
        ausências = objetoEmTeste.gerarAusencias(períodoDeAusência,"sem saco");
        assertEquals(1, ausências.size());
        
        inícioAusência = new DateTime(2013,11,18,17,00);
        finalAusência = new DateTime(2013,11,18,20,00);
        períodoDeAusência = new Interval(inícioAusência, finalAusência);
        
        ausências = objetoEmTeste.gerarAusencias(períodoDeAusência,"sem saco");
        assertEquals(1, ausências.size());
        
        inícioAusência = new DateTime(2013,11,18,17,00);
        finalAusência = new DateTime(2013,11,18,20,01);
        períodoDeAusência = new Interval(inícioAusência, finalAusência);
        
        ausências = objetoEmTeste.gerarAusencias(períodoDeAusência,"sem saco");
        assertEquals(2, ausências.size());
        
        inícioAusência = new DateTime(2013,11,18,00,00);
        finalAusência = new DateTime(2013,11,19,23,59);
        períodoDeAusência = new Interval(inícioAusência, finalAusência);
        
        ausências = objetoEmTeste.gerarAusencias(períodoDeAusência,"sem saco");
        assertEquals(2, ausências.size());
        
        inícioAusência = new DateTime(2013,11,18,00,00);
        finalAusência = new DateTime(2013,11,18,23,59);
        períodoDeAusência = new Interval(inícioAusência, finalAusência);
        
        ausências = objetoEmTeste.gerarAusencias(períodoDeAusência,"sem saco");
        assertEquals(2, ausências.size());
    }
}