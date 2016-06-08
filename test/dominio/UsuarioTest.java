/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import auxiliar.Perfil;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Rick
 */
public class UsuarioTest {

    private Usuario objetoEmTeste;
    private Perfil profile;
    private String usuario;
    private String Senha;
    
    public UsuarioTest() {
    }
    
    @Before
    public void setUp() {
        
        objetoEmTeste = Usuario.createUsuarioPadrao("Calebe");
        profile = Perfil.FUNCIONARIO;
        usuario = "Calebe";
        Senha = "123456";
    }
    
    @After
    public void tearDown() {
    }
    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void ValidaSenhaOk(){
        Assert.assertEquals(objetoEmTeste.getSenha(), Senha);
        //Alterando senha para teste negativo
        this.Senha = "1234567";
        Assert.assertNotSame(objetoEmTeste.getSenha(), this.Senha);
    }
   
    
    @Test
    public void ValidaPermissao(){
        Assert.assertEquals(objetoEmTeste.getPermissao(), profile);
        //Alterando profile para teste negativo
        this.profile = Perfil.ADMINISTRADOR;
        Assert.assertNotSame(objetoEmTeste.getPermissao(), this.profile);
    }
    
    @Test
    public void UsuarioNaoVazio(){
        Assert.assertNotNull(objetoEmTeste.getUsuario());
        this.usuario = null;
        Assert.assertNotSame(objetoEmTeste.getUsuario(), this.usuario);
    }
    
    @Test
    public void testeDevePermitirDefinirEConsultarIdDeUmUsuario(){        
        Long x = new Long("0");        
        assertEquals(null, objetoEmTeste.getId());
        
        objetoEmTeste.setId(x);
        assertEquals(x, objetoEmTeste.getId());
    }
    
    @Test
    public void testeDeveReportarCorretamenteToString(){
        Long x = new Long("0");    
        objetoEmTeste.setId(x);
        
        assertEquals("Dominio.Usuario[ id=0 ]", objetoEmTeste.toString());
        
        x = new Long("10");    
        objetoEmTeste.setId(x);
        assertEquals("Dominio.Usuario[ id=10 ]", objetoEmTeste.toString());        
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
        
        Usuario outro = Usuario.createUsuarioPadrao("Bob");
        assertTrue(objetoEmTeste.equals(outro));  
        
        outro.setId(x);
        assertFalse(objetoEmTeste.equals(outro));      
                
        objetoEmTeste.setId(x);
        assertTrue(objetoEmTeste.equals(outro));  
        
        outro = Usuario.createUsuarioPadrao("Bob");
        assertFalse(objetoEmTeste.equals(outro));       
    }    
}