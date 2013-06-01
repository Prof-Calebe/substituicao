/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import auxiliar.Perfil;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author Rick
 */
@Entity
public class Usuario implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    
    private Long id;
    private Perfil profile;
    private String Usuario;
    private String Senha;

    public Usuario() {
    }
    
    //Métodos a se fazer
    //Validar um Usuário e senha
    //Trocar o perfil de um usuário
    //
    
    public Usuario(String Usuario){
        this.profile = Perfil.FUNCIONARIO;
        this.Usuario = Usuario;
        this.Senha = "123456";
    }
    
    public Usuario(String Usuario, String Senha, Perfil profile){
        this.profile = profile;
        this.Usuario = Usuario;
        this.Senha = Senha;
    }
    
    public Usuario(String Usuario, String Senha){
        this.profile = Perfil.ADMINISTRADOR;
        this.Usuario = Usuario;
        this.Senha = Senha;
    }
    
    public String getUsuario(){
        return this.Usuario;
    }
    
    public String getSenha(){
        return this.Senha;
    }
    
    public Perfil getPermissao(){
        return this.profile;
    }
    
    public void setUsuario(String Nome){
        this.Usuario = Nome;
    }
    
    public void setSenha(String Senha){
        this.Senha = Senha;
    }
    
    
    public void setPermissao(Perfil profile){
        this.profile = profile;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dominio.Usuario[ id=" + id + " ]";
    }



    
    
    
}
