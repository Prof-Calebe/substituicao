/*
 * @see modelo
 */

package modelo;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Julio Bertolacini
 */
public class ProfessorModelTest {
    
    public ProfessorModelTest() {
    }
    
    @Test
    public void testingConstructor() {
        ProfessorModel pm = new ProfessorModel();
        assertNotNull(this);
    }
    
    @Test
    public void testingEqualsToSameInstance() {
        ProfessorModel pm = new ProfessorModel();
        ProfessorModel pm2 = pm;
        assertTrue(pm.equals(pm2));
    }
    
    @Test
    public void testingEqualsToNull() {
        ProfessorModel pm = new ProfessorModel();
        ProfessorModel pm2 = null;
        assertFalse(pm.equals(pm2));
    }
    
    @Test
    public void testingEqualsToSameName() {
        ProfessorModel pm = new ProfessorModel();
        ProfessorModel pm2 = new ProfessorModel();
        pm.Nome = "Bob";
        pm2.Nome = "Bob";
        assertTrue(pm.equals(pm2));
    }
    
}
