/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datamapper;

import datamapper.exceptions.NonexistentEntityException;
import dominio.Professor;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 *
 * @author Leticia
 */
public class ProfessorJpaController implements Serializable {

    private EntityManagerFactory emf = null;
    
    /**
     * <p>Constructor of the class ProfessorJpaController
     * This constructor receveis only one parameter, an EntityManagerFactory
     * and it sets the variable emf</p>
     * @param emf EntityManagerFactory
     */
    public ProfessorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     * <p>Get Entity Manager
     * This method is a simple getter to an EntityManager. It has no parameters.</p>
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
    }

    /**
     * <p>Create a Professor
     * Simply implements the creation of a Professor in the database, persisting and commiting it.
     * It has no returns, void method that receives an Object of a Professor as parameter</p>
     * @param professor object
     */
    public void create(Professor professor) {
        EntityManager em = null;
        try {
            em = this.getEntityManager();
            em.getTransaction().begin();
            em.persist(professor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * <p>Update Professor
     * This method implements the Update part of a database of the Entity Professor.
     * It receveis a professor Object, in case of this object doens't exist it throws an exception.
     * It is a void method.</p>
     * @param professor object
     * @throws NonexistentEntityException
     */
    public void edit(Professor professor) throws NonexistentEntityException{
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            professor = em.merge(professor);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = professor.getId();
                if (this.findProfessor(id) == null) {
                    throw new NonexistentEntityException("The professor with id " + id + " no longer exists.");
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
     * <p>Delete a Professor
     * This method receives the id of a Professor instead of the object in fact. It searches for
     * the respective professor and delete it. In case it doesn't exist an exception is thrown.</p>
     * @param id
     * @throws NonexistentEntityException
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Professor professor;
            try {
                professor = em.getReference(Professor.class, id);
                professor.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The professor with id " + id + " no longer exists.", enfe);
            }
            em.remove(professor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * <p>Find Professor Entities
     * This method only calls another one in the same class. Th only difference in this method are
     * the parameters. This one has no parameters and it returns a List of professor's objects.</p>
     * @return List of Professors
     */
    public List<Professor> findProfessorEntities() {
        return this.findProfessorEntities(true, -1, -1);
    }

    /**
     * <p>Find Professor Entities 2 
     * This method is called by the first Find Professor Entities method and it only calls another 
     * method with different parameters. This is only necessary because its a Public method that calls
     * a private one</p>
     * @param maxResults
     * @param firstResult
     * @return List of Professors
     */
    public List<Professor> findProfessorEntities(int maxResults, int firstResult) {
        return this.findProfessorEntities(false, maxResults, firstResult);
    }

    /**
     * <p>Find Professor Entities 3 
     * This method is called by the second Find Professor Entities method and it only calls another 
     * method with different parameters. This one acces the database and get as return a list of 
     * professors within the asked parameters.</p>
     * @param maxResults
     * @param firstResult
     * @return List of Professors
     */
    private List<Professor> findProfessorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = this.getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Professor as o");
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
     * <p>Find Professor
     * Search method thats uses Eager way to get a Professor, found by it Id, passed as
     * parameter.</p>
     * @param id
     * @return Professor object
     */
    public Professor findProfessor(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Professor.class, id);
        } finally {
            em.close();
        }
    }
    
    /**
     * <p>Find Professor by Name
     * This method search in the list of Professors returned by the method findProfessorEntities
     * the Professor with the same name as the parameter</p>
     * @param nome
     * @return Professor Object or null if it doesn't find any one
     */
    public Professor findProfessor(String nome){
        List<Professor> professores = this.findProfessorEntities();
        for(Professor professor : professores){
            if(professor.getNome().equals(nome)){
                return professor;
            }
        }
        
        return null;
    }
    
    /**
     * <p>Find Professor by Name
     * This method search in the list of Professors returned by the method findProfessorEntities
     * the Professor with the same username as the parameter</p>
     * @param username
     * @return Professor Object or null if it doesn't find any one
     */
    public Professor findProfessorPorUsername(String username){
        List<Professor> professores = this.findProfessorEntities();
        for(Professor professor : professores){
            if(professor.getUsername().equals(username)){
                return professor;
            }
        }
        
        return null;
    }    

    /**
     * <p>Count Professors
     * This method search in the database all professors and count then, so it can return the int 
     * total number of professors</p>
     * @return the number of Professors in total
     */
    public int getProfessorCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Professor as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}