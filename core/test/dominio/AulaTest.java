/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;
//import prosub.dominio.Aula;

import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Interval;
//import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Leticia
 */
public class AulaTest {
    
    private Aula objetoEmTeste;
    private Interval periodo;
    private Interval periodo2;

    int teste;
    
    public AulaTest() {
    }
    
    @Before
    public void setUp() {
       
        DateTime limiteInfP1 = new DateTime(2013, 1, 1, 20, 0);
        DateTime limiteSupP1 = new DateTime(2013, 1, 1, 20, 50);
        
        periodo = new Interval(limiteInfP1, limiteSupP1);
        
        DateTime limiteInfP2 = new DateTime(2013, 1, 1, 20, 20);
        DateTime limiteSupP2 = new DateTime(2013, 1, 1, 21, 0);
                
        periodo2 = new Interval(limiteInfP2, limiteSupP2);
        
        objetoEmTeste = new Aula("Teste de Software", DateTimeConstants.MONDAY, periodo);
    }

    @Test
    public void deveSerInicializadaComDiaDaSemanaEPeriodo() {
        
        Assert.assertEquals(objetoEmTeste.getDiaDaSemana(), DateTimeConstants.MONDAY);
        Assert.assertEquals(objetoEmTeste.getPeriodo(), periodo);
    }
    
    @Test
    public void testeDeveSerPossivelVerSeDuasAulasBatemNosDiasOuHorarios(){
        
        Aula aula2 = new Aula("Nome", DateTimeConstants.MONDAY, periodo2);
        
        Assert.assertTrue(objetoEmTeste.bateCom(aula2));
    }
    
    @Test
    public void testeDeveSerPossivelVerSeUmPeriodoBateComUmaAula(){
        
        Assert.assertTrue(objetoEmTeste.bateHorarioCom(periodo2));
        
        DateTime inicio = new DateTime(2013, 05, 28, 20,51);
        DateTime fim = new DateTime(2013, 05, 28, 22, 50);
        
        Interval outroPeriodo = new Interval(inicio, fim);
        
        Assert.assertFalse(objetoEmTeste.bateHorarioCom(outroPeriodo));
    }
    
    @Test
    public void testeDeveSerAvaliadaIgualÀOutraAulaSomenteSeCasarEmDiaDaSemanaEPeríodo(){
        
        objetoEmTeste = new Aula("Nome", DateTimeConstants.MONDAY, periodo);
        Aula outraAula = new Aula("Nome", DateTimeConstants.MONDAY, periodo);        
        assertTrue(objetoEmTeste.equals(outraAula));
        
        outraAula = new Aula("Nome", DateTimeConstants.MONDAY, periodo2);
        assertFalse(objetoEmTeste.equals(outraAula));
        
        outraAula = new Aula("Nome", DateTimeConstants.TUESDAY, periodo);
        assertFalse(objetoEmTeste.equals(outraAula));    
    }
    
    @Test
    public void testeDevePermitirDefinirEConsultarIdDeUmaAula(){        
        Long x = new Long("0");        
        assertEquals(null, objetoEmTeste.getId());
        
        objetoEmTeste.setId(x);
        assertEquals(x, objetoEmTeste.getId());
    }
    
    @Test
    public void testeDeveReportarCorretamenteToString(){
        Long x = new Long("0");    
        objetoEmTeste.setId(x);
        
        assertEquals("Dominio.Aula[ id=0 ]", objetoEmTeste.toString());
        
        x = new Long("10");    
        objetoEmTeste.setId(x);
        assertEquals("Dominio.Aula[ id=10 ]", objetoEmTeste.toString());        
    }
    
    @Test
    public void testeDeveReportarCorretamenteOHashCode(){
        int hashCode = periodo.hashCode() + DateTimeConstants.MONDAY * 31;
        assertEquals(hashCode, objetoEmTeste.hashCode());
    }
    
    @Test
    public void testeDeveInformarCorretamenteSeuNome() {
        assertEquals("Teste de Software", objetoEmTeste.getNome());
    }
}