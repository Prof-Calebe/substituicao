/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package modelo;

/**
 *
 * @author Rick
 */
public class ProfessorModel {
    
    public String Nome;
    public Long id;

    public ProfessorModel() {
    }
    
    @Override
    public boolean equals(Object obj){
        
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
    public int hashCode(){
        int result = 1;
        result = 31*result + ( (this.Nome == null) ? 0 : this.Nome.hashCode() );
        return result;
    }
    
}
