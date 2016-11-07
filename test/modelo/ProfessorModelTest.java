/**
 * @see modelo
 */

package modelo;

import org.junit.Test;

import org.junit.Assert;

/**
 *
 * @author Julio Bertolacini
 */
public class ProfessorModelTest {
    
    /**
     * Default constructor
     */
    public ProfessorModelTest() {
    }
    
    /**
     * Testing the contructor method.
     */
    @Test
    public void testingConstructor() {
        ProfessorModel pm = new ProfessorModel();
        Assert.assertNotNull(pm);
    }
    
    /**
     * Testing "eguals" method for comparing the same instance object
     */
    @Test
    public void testingEqualsToSameInstance() {
        ProfessorModel pm = new ProfessorModel();
        ProfessorModel pm2 = pm;
        Assert.assertTrue(pm.equals(pm2));
    }
    
    /**
     * Testing "equals" method for comparing to a null reference
     */
    @Test
    public void testingEqualsToNull() {
        ProfessorModel pm = new ProfessorModel();
        ProfessorModel pm2 = null;
        Assert.assertFalse(pm.equals(pm2));
    }
    
    /**
     * Testing "equals" method for comparing to the same instance
     */
    @Test
    public void testingEqualsToSameName() {
        ProfessorModel pm = new ProfessorModel();
        ProfessorModel pm2 = new ProfessorModel();
        final String name = "Bob";
        pm.Nome = name;
        pm2.Nome = name;
        Assert.assertTrue(pm.equals(pm2));
    }
    
}
