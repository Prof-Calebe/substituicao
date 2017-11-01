package datamapper;
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import datamapper.exceptions.NonexistentEntityException;
import dominio.Usuario;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

/**
 *
 * @author Rick
 */
public class UsuarioJpaController implements Serializable {

    /**
     * Fábrica usada para obter instâncias de EntityManager
     */
    private EntityManagerFactory emf;

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    /**
     *
     * @param u Novo registro de Usuário para ser inserido na base
     * @throws PersistenceException - Exception genérica de operações do
     * EntityManager
     */
    public void create(Usuario u) throws PersistenceException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(u);
            em.getTransaction().commit();
        } finally {
            if (em != null && !em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     *
     * @param u Usuário a ser merged na base.
     * @throws PersistenceException - Exception genérica de operações do
     * EntityManager
     */
    public void edit(Usuario u) throws PersistenceException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.merge(u);
            em.getTransaction().commit();
        } finally {
            if (em != null && !em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     *
     * @param id Chave primária de um registro existente de Usuario
     * @throws NonexistentEntityException Registro de Usuario não existe
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getId();
                em.remove(usuario);
                em.getTransaction().commit();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
        } finally {
            if (em != null && !em.isOpen()) {
                em.close();
            }
        }
    }

    /**
     *
     * @return Retorna todos registros de Usuario na base
     */
    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    /**
     *
     * @param maxResults Numero máximo de registros retornados
     * @param firstResult A partir de qual registro deve ser retornado
     * @return registros de Usuario encontrados na base
     */
    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Usuario as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param id Chave primária de um registro existente de Usuario
     * @return Retorna registro encontrado ou null
     */
    public Usuario findUsuario(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    /**
     *
     * @param nome Busca registro de Usuario por nome
     * @return Retorna registro encontrado ou null
     */
    public Usuario findUsuario(String nome) {
        List<Usuario> usuarios = this.findUsuarioEntities();
        for (Usuario usuario : usuarios) {
            if (usuario.getUsuario().equals(nome)) {
                return usuario;
            }
        }
        return null;
    }

    /**
     *
     * @return Numero de registros de Usuario na base
     */
    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Usuario as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
