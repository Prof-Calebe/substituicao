/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import auxiliar.Perfil;
import datamapper.UsuarioJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Usuario;
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
    private final EntityManagerFactory emf;

    public AdministracaoDeUsuariosService() {

        emf = Persistence.createEntityManagerFactory("pro_subPU");
        //controller = new UsuarioJpaController(emf);
    }

    public void salvarUsuario(String Nome, String Senha, Perfil profile) {
        UsuarioJpaController controller = new UsuarioJpaController(emf);
        Usuario usuario = Usuario.createUsuarioPadrao(Nome);
        usuario.setSenha(Senha);
        usuario.setPermissao(profile);
        controller.create(usuario);
    }

    public void editarUsuario(String senha, Perfil profile, Long id)
            throws NonexistentEntityException, Exception {

        UsuarioJpaController controller = new UsuarioJpaController(emf);
        Usuario usuarioAEditar = controller.findUsuario(id);

        usuarioAEditar.setUsuario(usuarioAEditar.getUsuario());
        usuarioAEditar.setSenha(senha);
        usuarioAEditar.setPermissao(profile);

        controller.edit(usuarioAEditar);
    }

    public List<Usuario> listarUsuarios() {

        UsuarioJpaController controller = new UsuarioJpaController(emf);
        List<Usuario> usuarios = controller.findUsuarioEntities();
        List<Usuario> modelos = new LinkedList<>();

        for (Usuario usuario : usuarios) {
            Usuario model = new Usuario();
            model.setUsuario(usuario.getUsuario());
            model.setSenha(usuario.getSenha());
            model.setPermissao(usuario.getPermissao());
            model.setId(usuario.getId());
            modelos.add(model);
        }
        return modelos;
    }

    public Usuario obterUsuario(String nome) {

        UsuarioJpaController controller = new UsuarioJpaController(emf);

        Usuario usuario = controller.findUsuario(nome);

        Usuario model = new Usuario();
        model.setUsuario(usuario.getUsuario());
        model.setSenha(usuario.getSenha());
        model.setPermissao(usuario.getPermissao());
        model.setId(usuario.getId());

        return usuario;

    }

}
