/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package datamapper;

import datamapper.exceptions.NonexistentEntityException;
import dominio.Ausencia;
import dominio.Professor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;

/**
 * @author Leticia
 */

public class AusenciaJpaController implements Serializable {

    private EntityManagerFactory emf;

    /**
     * Constructor method
     * @param emf EntityManagerFactory object
     */
    public AusenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }

    /**
     *
     * @return Return an EntityManager
     */
    public EntityManager getEntityManager() {
        return this.emf.createEntityManager();
        
    }
   
    /**
     * This method persists an Ausencia Object on the DataBase
     * @param ausencia Is a Ausencia Object.
     */
    public void create(Ausencia ausencia) {
        EntityManager em = null;
        try {
            em = this.getEntityManager();
            em.getTransaction().begin();
            em.persist(ausencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * This Method tryes to override an already existent object Ausencia in the DataBase with the same Id. It's an update.
     * @param ausencia Is an Ausencia Object.
     * @throws NonexistentEntityException This is thrown when the object no longer exists in the DataBase.
     */
    public void edit(Ausencia ausencia) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = this.getEntityManager();
            em.getTransaction().begin();
            ausencia = em.merge(ausencia);
            em.getTransaction().commit();
        } catch (EntityNotFoundException enfe) {
            String msg = enfe.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ausencia.getId();
                if (this.findAusencia(id) == null) {
                    throw new NonexistentEntityException("The ausencia with id " + id + " no longer exists. Object ausencia could not be changed.", enfe);
                }
            }
            throw enfe;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * This method tryes to remove an existing object from the DataBase based on it's id. 
     * @param id It's an identificator for the Object on the DataBase.
     * @throws NonexistentEntityException This is thrown when the object no longer exists in the DataBase.
     */
    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = this.getEntityManager();
            em.getTransaction().begin();
            Ausencia ausencia;
            try {
                ausencia = em.getReference(Ausencia.class, id);
                ausencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ausencia with id " + id + " no longer exists. Object ausencia could not be destroyed.", enfe);
            }
            em.remove(ausencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * This method lists all Ausencia Objects on the DataBase.
     * @return Returns all Ausencia objects.
     */
    public List<Ausencia> findAusenciaEntities() {
        return this.findAusenciaEntities(true, -1, -1);
        
    }

    /**
     * This method lists some Ausencia Objects on the DataBase utilizing this parameters of search.
     * @param maxResults It's an int for the maxResults.
     * @param firstResult It's an int apointing the first result.
     * @return Retruns a list of Ausencia Objects based on the parameters.
     */
    public List<Ausencia> findAusenciaEntities(int maxResults, int firstResult) {
        return this.findAusenciaEntities(false, maxResults, firstResult);
    }
    
    /**
     * @param prof A professor object
     * @return A list of ausencias from this professor object.
     */
    public List<Ausencia> listAusenciasPorProfessor(Professor prof) {
        List<Ausencia> ausencias = this.findAusenciaEntities();

        List<Ausencia> ausenciasComProfessor = new ArrayList<>();

        for (Ausencia ausencia : ausencias) {

            if (ausencia.getProfessor().equals(prof)) {
                ausenciasComProfessor.add(ausencia);
            }

        }
        
        return ausenciasComProfessor;
    }

    /**
     * @param professor It's a professor object.
     * @return A list of Ausencia Objects based on IndicacaoDeSubstituto.
     */
    public List<Ausencia> listAusenciasPorIndicacaoDeSubstituto(Professor professor) {
        List<Ausencia> ausencias = this.findAusenciaEntities();

        List<Ausencia> ausenciasComProfessor = new ArrayList<>();

        for (Ausencia ausencia : ausencias) {

            if (ausencia.getIndicacoesSubstitutos().contains(professor)) {
                ausenciasComProfessor.add(ausencia);
            }

        }

        return ausenciasComProfessor;
    }

    /**
     * @param professor It's a professor object.
     * @return A List of Ausencia of Professores of type Substituto. 
     */
    public List<Ausencia> listAusenciasPorSubstituto(Professor professor) {
        List<Ausencia> ausencias = this.findAusenciaEntities();

        List<Ausencia> ausenciasComProfessor = new ArrayList<>();

        for (Ausencia ausencia : ausencias) {

            if (ausencia.getProfessorSubstituto().equals(professor)) {
                ausenciasComProfessor.add(ausencia);
            }

        }

        return ausenciasComProfessor;
    }
    
     /**
     *
     * @param all It's a Boolean, true for all and false for especifics.
     * @param maxResults It's an int for the Max Results
     * @param firstResult It's an int for the first Result
     * @return A List of Ausencia Objects. 
     */
    private List<Ausencia> findAusenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = this.getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Ausencia as o");
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
     * @param id The identificator of the Ausencia Object you're looking for.
     * @return An Ausencia object, if it is found. 
     * @throws NonexistentEntityException This is thrown when the object no longer exists in the DataBase.
     */
    public Ausencia findAusencia(Long id) throws NonexistentEntityException {
        EntityManager em = this.getEntityManager();
        try {
            return em.find(Ausencia.class, id);
        }catch(EntityNotFoundException enfe){
             throw new NonexistentEntityException("The ausencia with id " + id + " no longer exists. Object ausencia could not be found.", enfe);
        } finally {
            em.close();
        }
    }

    /**
     * @param codigo It's an ID of the Ausencia Object
     * @return The Ausencia Object itself. 
     */
    public Ausencia findAusencia(String codigo) {
        List<Ausencia> ausencias = this.findAusenciaEntities();
        for (Ausencia ausencia : ausencias) {
            if (ausencia.getCodigo().equals(codigo)) {
                return ausencia;
            }
        }

        return null;
    }

    /**
     * This method counts the number of Ausencias Object in the DataBse.
     * @return The number of Ausencias Object in the DataBase. 
     */
    public int getAusenciaCount() {
        EntityManager em = this.getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Ausencia as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
