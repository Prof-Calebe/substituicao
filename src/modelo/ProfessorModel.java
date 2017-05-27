/**
 * @see modelo
 * 
 */

package modelo;

/**
 *
 * @author Rick
 */
public class ProfessorModel {
    
    /**
     * Attribute:  nome
     */
    public String Nome;
    
    /**
     * Attribute: id
     */
    public Long id;

    /**
     * Default constructor
     */
    public ProfessorModel() {
    }
    
    @Override
    public boolean equals(Object obj) {
        
        if (obj == this) {
            return true;
        }
        
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }
        
        ProfessorModel outroObj = (ProfessorModel) obj;
        
        return this.Nome.equals(outroObj.Nome);
    }
    
    @Override
    public int hashCode() {
        int result = 1;
        final int hashMultiplier = 31; 
        result = hashMultiplier * result + ( (this.Nome == null) ? 0 : this.Nome.hashCode() );
        return result;
    }
    
}
