/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package datamapper;

import datamapper.exceptions.NonexistentEntityException;
import dominio.Aula;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Leticia
 */
public class AulaJpaController implements Serializable {
    
    /**
     *  <p> Attribute used for represent a EntityManagerFactory </p>
     * @param emf Object of type EntityManagerFactory
     */
    private EntityManagerFactory emf;
    /**
     * <p>Constructor's class, used to construct a object representation
     * of this class</p>
     * @param emf EntityManagerFactory object
     */
    public AulaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    /**
     * <p>A get method, used to create a EntityManager and return it</p>
     * @return emf EntityManager object
     */
    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    /**
     * <p>Method used to create the representation of object of type Aula and
     * insert into database</p>
     * @param aula Object that represent the Aula class
     */
    public void create(Aula aula) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(aula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    
    /**
     * <p>Method used to update an existence of object representation in database</p>
     * @param aula Object that represent the Aula class
     * @throws NonexistentEntityException 
     */
    public void edit(Aula aula) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            aula = em.merge(aula);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = aula.getId();
                if (findAula(id) == null) {
                    throw new NonexistentEntityException("The aula with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    /**
     * <p>Method used to delete the especific object in database by id</p>
     * @param id Long variable used to indentify an unique Aula object
     * @throws NonexistentEntityException 
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Aula aula;
            try {
                aula = em.getReference(Aula.class, id);
                aula.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The aula with id " + id + " no longer exists.", enfe);
            }
            em.remove(aula);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
    /**
     * <p>Method used to return a List of all objects of type Aula</p>
     * @return List of Aula entities
     */
    public List<Aula> findAulaEntities() {
        return findAulaEntities(true, -1, -1);
    }
    
    /**
     * <p>Method used to return a List of objects of type Aula</p>
     * @param maxResults Int variable used to control the maximum result of return list
     * @param firstResult Int Variable used to set where the list starts
     * @return List of Aula entities
     */
    public List<Aula> findAulaEntities(int maxResults, int firstResult) {
        return findAulaEntities(false, maxResults, firstResult);
    }
    /**
     * <p>Method used to return a List of objects of type Aula</p>
     * @param all Boolean used to verify if return all of entities or not
     * @param maxResults Int variable used to control the maximum result of return list
     * @param firstResult Int Variable used to set where the list starts
     * @return List of Aula entities
     */
    private List<Aula> findAulaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Aula.class));
            Query q = em.createQuery(cq);
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
     * <p>Simple method to find a specific Aula by id</p>
     * @param id Long used to represent an unique object representation of Aula
     * @return Object of type Aula
     */
    public Aula findAula(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Aula.class, id);
        } finally {
            em.close();
        }
    }
    /**
     * <p>Method used to count how many object of Aula exist in the database</p>
     * @return Int number of objects of Aula in the database 
     */
    public int getAulaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Aula> rt = cq.from(Aula.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
