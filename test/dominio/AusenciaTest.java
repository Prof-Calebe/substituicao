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

/**
 *
 * @author Leticia
 */
public class AusenciaTest {
    
    private Professor professor;
    private Professor professorSubstituto;
    private Interval periodo;
    private Ausencia ausencia;
    private String motivo;
    private EstadoAusencia estado;
    
    public AusenciaTest() {
    }
    
    @Before
    public void setUp() {
        
        professor = EasyMock.createMock(Professor.class);
        professorSubstituto = EasyMock.createMock(Professor.class);
        motivo = "Congresso internacional";
        ausencia = new Ausencia("1234", periodo, professor, motivo);
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
        Assert.assertEquals(ausencia.getCodigo(), "1234");
        Assert.assertEquals(ausencia.getPeriodo(), periodo);
        Assert.assertEquals(ausencia.getProfessor(), professor);
        Assert.assertEquals(ausencia.getMotivo(), motivo);
        Assert.assertEquals(0, ausencia.getIndicacoesSubstitutos().size());
        Assert.assertEquals(ausencia.getEstado(), estado);
        Assert.assertEquals(null, ausencia.getProfessorSubstituto());
    }
    
    @Test
    public void testeDeveSerPossívelIndicarProfessoresSubstitutoEmUmaAusencia(){
        ausencia.indicarSubstituto(professorSubstituto);
        
        
        Assert.assertEquals(1, ausencia.getIndicacoesSubstitutos().size());
        
        Assert.assertTrue(ausencia.getIndicacoesSubstitutos().contains(professorSubstituto));
    }
    
    @Test
    public void testeDeveSerPossívelDefinirSubstitutoEmUmaAusencia(){
        ausencia.setProfessorSubstituto(professorSubstituto);
        
        
        Assert.assertEquals(professorSubstituto, ausencia.getProfessorSubstituto());
        

    }
    
    
    @Test(expected=IllegalStateException.class)
    public void testeNaoEhPossivelIndicarProprioProfessorAusenteComoSubstituto(){
        ausencia.indicarSubstituto(professor);
    }

    @Test(expected=IllegalStateException.class)
    public void testeNaoEhPossivelDefinirProprioProfessorAusenteComoSubstituto(){
        ausencia.setProfessorSubstituto(professor);
    }
    
    @Test(expected=IllegalStateException.class)
    public void testeNaoEhPossivelRetirarUmProfessorSubstitutoQueNaoEstejaNaListaDeProfsSubstitutos(){
        ausencia.retirarSubstituto(professorSubstituto);
    }
    
    @Test
    public void testeDeveSerPossívelRetirarUmProfessorSubstitutoDaLista(){
        ausencia.indicarSubstituto(professorSubstituto);
        
        Assert.assertEquals(1, ausencia.getIndicacoesSubstitutos().size());
        
        Assert.assertTrue(ausencia.getIndicacoesSubstitutos().contains(professorSubstituto));
        
        ausencia.retirarSubstituto(professorSubstituto);
        
        Assert.assertEquals(0, ausencia.getIndicacoesSubstitutos().size());
        
        Assert.assertFalse(ausencia.getIndicacoesSubstitutos().contains(professorSubstituto));        
        
    }
    
    @Test
    public void testeDeveSerPossivelCancelarUmaAusencia(){
        
        Assert.assertEquals(EstadoAusencia.Alocacao_Pendente, ausencia.getEstado());
        
        ausencia.cancelarAusencia();
        
        Assert.assertEquals(EstadoAusencia.Ausencia_Cancelada, ausencia.getEstado());
        
    }
    @Test
    public void testeDeveSerPossivelCancelarAulasRelativasAUmaAusencia(){
        
        Assert.assertEquals(EstadoAusencia.Alocacao_Pendente, ausencia.getEstado());
        
        ausencia.cancelarAulas();
        
        Assert.assertEquals(EstadoAusencia.Aulas_Canceladas, ausencia.getEstado());
        
    }
    
    @Test
    public void testeDeveSerPossivelDefinirComoAlocada(){
        
        Assert.assertEquals(EstadoAusencia.Alocacao_Pendente, ausencia.getEstado());
        
        ausencia.definirComoAlocado();
        
        Assert.assertEquals(EstadoAusencia.Alocacao_Efetuada, ausencia.getEstado());
        
    }
    
    
    
}