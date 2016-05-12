/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dominio;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author prgoes
 */
public class EstadoAusenciaTest {
    
    public EstadoAusenciaTest() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testeDeveRetornarCódigoCorretamenteEmFunçãoDaConstrução() {
    
        EstadoAusencia estado = EstadoAusencia.Alocacao_Efetuada;
        assertEquals(1, estado.getCode());
        
        estado = EstadoAusencia.Ausencia_Cancelada;
        assertEquals(2, estado.getCode());
        
        estado = EstadoAusencia.Alocacao_Pendente;
        assertEquals(3, estado.getCode());
        
        estado = EstadoAusencia.Aulas_Canceladas;
        assertEquals(4, estado.getCode());                
    }
    
    @Test
    public void testeDeveSerConstruídoCorretamenteAPartirdeValueOf() {
    
        EstadoAusencia estado = EstadoAusencia.valueOf("Alocacao_Efetuada");
        assertEquals(1, estado.getCode());
        
        estado = EstadoAusencia.valueOf("Ausencia_Cancelada");
        assertEquals(2, estado.getCode());
        
        estado = EstadoAusencia.valueOf("Alocacao_Pendente");
        assertEquals(3, estado.getCode());
        
        estado = EstadoAusencia.valueOf("Aulas_Canceladas");
        assertEquals(4, estado.getCode());  
    }
}
