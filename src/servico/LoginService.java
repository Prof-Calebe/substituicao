/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.UsuarioJpaController;
import dominio.Usuario;
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

    /**
     * Confere se usuário e senha batem com os registros no banco
     * @param usuario Usuario a ser buscado
     * @param senha Senha a ser conferida
     * @return true se existir usuário e senha for correta, false caso contrário
     */
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
