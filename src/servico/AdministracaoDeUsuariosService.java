/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import auxiliar.Perfil;
import datamapper.UsuarioJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Usuario;
import modelo.UsuarioModel;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rick
 */
public class AdministracaoDeUsuariosService {
    
   //private UsuarioJpaController controller;
    private EntityManagerFactory emf;
    
    public AdministracaoDeUsuariosService(){
        
        emf = Persistence.createEntityManagerFactory("pro_subPU");
        //controller = new UsuarioJpaController(emf);
    }
    
    public void SalvarUsuario(String Nome, String Senha, Perfil profile){
        UsuarioJpaController controller = new UsuarioJpaController(emf);
        Usuario usuario = Usuario.createUsuarioPadrao(Nome);
        usuario.setSenha(Senha);
        usuario.setPermissao(profile);
        controller.create(usuario);
    }
    
    public void EditarUsuario(String senha, Perfil profile, Long id) throws NonexistentEntityException, Exception{
        
        UsuarioJpaController controller = new UsuarioJpaController(emf);
        Usuario usuarioAEditar = controller.findUsuario(id);
        
        usuarioAEditar.setUsuario(usuarioAEditar.getUsuario());
        usuarioAEditar.setSenha(senha);
        usuarioAEditar.setPermissao(profile);
        
        controller.edit(usuarioAEditar);
    }
    
    public List<UsuarioModel> ListarUsuarios(){
        
        UsuarioJpaController controller = new UsuarioJpaController(emf);
        List<Usuario> usuarios = controller.findUsuarioEntities();
        List<UsuarioModel> modelos = new LinkedList<UsuarioModel>();
        
        for (Usuario usuario : usuarios){
            UsuarioModel model = new UsuarioModel();
            model.Usuario = usuario.getUsuario();
            model.Senha = usuario.getSenha();
            model.profile = usuario.getPermissao();
            model.id = usuario.getId();
            modelos.add(model);
        }
        return modelos;   
    }
    
    public UsuarioModel obterUsuario(String nome){
        
        UsuarioJpaController controller = new UsuarioJpaController(emf);
        
        Usuario usuario = controller.findUsuario(nome);
        
        UsuarioModel model = new UsuarioModel();
        model.Usuario = usuario.getUsuario();
        model.Senha = usuario.getSenha();
        model.profile = usuario.getPermissao();
        model.id = usuario.getId();
        
        return model;
        
    }
    
    
}
