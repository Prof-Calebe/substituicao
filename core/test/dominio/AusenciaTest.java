package dominio;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import dominio.Professor;
import java.lang.reflect.Field;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.easymock.*;
import org.joda.time.Interval;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Leticia
 */
public class AusenciaTest {
    
    private Professor professor;
    private Professor professorSubstituto;
    private Aula aulaPerdida;
    private Interval periodo;
    private Ausencia objetoEmTeste;
    private String motivo;
    private EstadoAusencia estado;
    
    public AusenciaTest() {
    }
    
    @Before
    public void setUp() {
        
        professor = EasyMock.createMock(Professor.class);
        professorSubstituto = EasyMock.createMock(Professor.class);
        motivo = "Congresso internacional";
        aulaPerdida = EasyMock.createMock(Aula.class);
        objetoEmTeste = new Ausencia("1234", periodo, professor, motivo, aulaPerdida);
        estado = EstadoAusencia.Alocacao_Pendente;
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    @Test
    public void testeDeveSerInicializadoComPeriodoProfessorMotivoESemProfessorSubstituto() {
        Assert.assertEquals(objetoEmTeste.getCodigo(), "1234");
        Assert.assertEquals(objetoEmTeste.getPeriodo(), periodo);
        Assert.assertEquals(objetoEmTeste.getProfessor(), professor);
        Assert.assertEquals(objetoEmTeste.getMotivo(), motivo);
        Assert.assertEquals(0, objetoEmTeste.getIndicacoesSubstitutos().size());
        Assert.assertEquals(objetoEmTeste.getEstado(), estado);
        Assert.assertEquals(null, objetoEmTeste.getProfessorSubstituto());
    }
    
    @Test
    public void testeDeveSerPossívelIndicarProfessoresSubstitutoEmUmaAusencia(){
        objetoEmTeste.indicarSubstituto(professorSubstituto);
        
        
        Assert.assertEquals(1, objetoEmTeste.getIndicacoesSubstitutos().size());
        
        Assert.assertTrue(objetoEmTeste.getIndicacoesSubstitutos().contains(professorSubstituto));
    }
    
    @Test
    public void testeDeveSerPossívelDefinirSubstitutoEmUmaAusencia(){
        objetoEmTeste.setProfessorSubstituto(professorSubstituto);       
        
        Assert.assertEquals(professorSubstituto, objetoEmTeste.getProfessorSubstituto());
    }
    
    @Test
    public void testeDeveSerPossívelConfirmarUmaAusênciaComSubstituto(){
        objetoEmTeste.setProfessorSubstituto(professorSubstituto);       
        objetoEmTeste.confirmar();
        assertEquals(objetoEmTeste.getEstado(), EstadoAusencia.Alocacao_Confirmada);
    }
    
    @Test(expected=IllegalStateException.class) 
    public void testeNãoDeveSerPossívelConfirmarAusênciaSemProfessorDeclarado(){
        objetoEmTeste.confirmar();
    }
    
    @Test
    public void testeDeveSerPossívelRecusarUmaAusênciaComSubstituto(){
        objetoEmTeste.setProfessorSubstituto(professorSubstituto);       
        objetoEmTeste.recusar();
        assertEquals(objetoEmTeste.getEstado(), EstadoAusencia.Alocacao_Pendente);
        org.junit.Assert.assertNull(objetoEmTeste.getProfessorSubstituto());
    }
    
    @Test(expected=IllegalStateException.class) 
    public void testeNãoDeveSerPossívelRecusarAusênciaSemProfessorDeclarado(){
        objetoEmTeste.recusar();
    }
    
    
    @Test(expected=IllegalStateException.class)
    public void testeNaoEhPossivelIndicarProprioProfessorAusenteComoSubstituto(){
        objetoEmTeste.indicarSubstituto(professor);
    }

    @Test(expected=IllegalStateException.class)
    public void testeNaoEhPossivelDefinirProprioProfessorAusenteComoSubstituto(){
        objetoEmTeste.setProfessorSubstituto(professor);
    }
    
    @Test(expected=IllegalStateException.class)
    public void testeNaoEhPossivelRetirarUmProfessorSubstitutoQueNaoEstejaNaListaDeProfsSubstitutos(){
        objetoEmTeste.retirarSubstituto(professorSubstituto);
    }
    
    @Test
    public void testeDeveSerPossívelRetirarUmProfessorSubstitutoDaLista(){
        objetoEmTeste.indicarSubstituto(professorSubstituto);
        
        Assert.assertEquals(1, objetoEmTeste.getIndicacoesSubstitutos().size());
        
        Assert.assertTrue(objetoEmTeste.getIndicacoesSubstitutos().contains(professorSubstituto));
        
        objetoEmTeste.retirarSubstituto(professorSubstituto);
        
        Assert.assertEquals(0, objetoEmTeste.getIndicacoesSubstitutos().size());
        
        Assert.assertFalse(objetoEmTeste.getIndicacoesSubstitutos().contains(professorSubstituto));        
        
    }
    
    @Test
    public void testeDeveSerPossivelCancelarUmaAusencia(){
        
        Assert.assertEquals(EstadoAusencia.Alocacao_Pendente, objetoEmTeste.getEstado());
        
        objetoEmTeste.cancelarAusencia();
        
        Assert.assertEquals(EstadoAusencia.Ausencia_Cancelada, objetoEmTeste.getEstado());
        
    }
    @Test
    public void testeDeveSerPossivelCancelarAulasRelativasAUmaAusencia(){
        
        Assert.assertEquals(EstadoAusencia.Alocacao_Pendente, objetoEmTeste.getEstado());
        
        objetoEmTeste.cancelarAulas();
        
        Assert.assertEquals(EstadoAusencia.Aulas_Canceladas, objetoEmTeste.getEstado());
        
    }
    
    @Test
    public void testeDeveSerPossivelDefinirComoAlocada(){
        
        Assert.assertEquals(EstadoAusencia.Alocacao_Pendente, objetoEmTeste.getEstado());
        
        objetoEmTeste.definirComoAlocado();
        
        Assert.assertEquals(EstadoAusencia.Alocacao_Efetuada, objetoEmTeste.getEstado());
        
    }
    
    @Test
    public void testeDevePermitirDefinirEConsultarIdDeUmaAusencia(){        
        Long x = new Long("0");        
        assertEquals(null, objetoEmTeste.getId());
        
        objetoEmTeste.setId(x);
        assertEquals(x, objetoEmTeste.getId());
    }
    
    @Test
    public void testeDeveReportarCorretamenteToString(){
        Long x = new Long("0");    
        objetoEmTeste.setId(x);
        
        assertEquals("Dominio.Ausencia[ id=0 ]", objetoEmTeste.toString());
        
        x = new Long("10");    
        objetoEmTeste.setId(x);
        assertEquals("Dominio.Ausencia[ id=10 ]", objetoEmTeste.toString());        
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
    
    @Test
    public void testeDeveSerConsideradoIgualSomenteAOutroProfessorDeIdIdentica()
    {
        Long x = new Long("0");  
        assertFalse(objetoEmTeste.equals(x));           
        
        Ausencia outro = new Ausencia("1234", periodo, professor, motivo, aulaPerdida);
        assertTrue(objetoEmTeste.equals(outro));  
        
        outro.setId(x);
        assertFalse(objetoEmTeste.equals(outro));      
                
        objetoEmTeste.setId(x);
        assertTrue(objetoEmTeste.equals(outro));  
        
        outro = new Ausencia("1234", periodo, professor, motivo, aulaPerdida);
        assertFalse(objetoEmTeste.equals(outro));       
    }   
    
    @Test
    public void testeDevePermitirDefinirEConsultarUmMotivo()
    {
        objetoEmTeste.setMotivo("Um bom motivo");
        assertEquals("Um bom motivo", objetoEmTeste.getMotivo());
    }
    
    @Test
    public void testeDevePermitirDefinirEConsultarUmMotivoDeRejeição()
    {
        objetoEmTeste.setMotivoRejeicao("Um bom motivo");
        assertEquals("Um bom motivo", objetoEmTeste.getMotivoRejeicao());
    }
    
    @Test
    public void testeDevePermitirDefinirEConsultarUmProfessor()
    {
        objetoEmTeste.setProfessor(professor);
        assertEquals(professor, objetoEmTeste.getProfessor());
    }
    
    @Test
    public void testeDeveInformarAAulaPerdida()
    {
        assertEquals(aulaPerdida, objetoEmTeste.getAula());
    }
}