/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import dominio.Usuario;
import datamapper.UsuarioJpaController;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


/**
 *
 * @author Rick
 */
public class LoginService {
    
    private final UsuarioJpaController controller;
    
    public LoginService(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        controller = new UsuarioJpaController(emf);
    }
    
    public boolean verificarUsuarioESenha(String usuario, String senha){

        List<Usuario> ListaDeUsuario = this.controller.findUsuarioEntities();
        
        for (Usuario user : ListaDeUsuario){
            if(user.getUsuario().equals(usuario) && user.getSenha().equals(senha))
            {
                return true;
            }
        }
        return false;
    }
    
}
