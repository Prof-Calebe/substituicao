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
 *
 * @author Leticia
 */
public class AusenciaJpaController implements Serializable {

    private EntityManagerFactory emf;

    /**
     *
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
     *
     * @param ausencia Is a Ausencia Object.
     * This method persists an Ausencia Object on the DataBase
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
     *
     * @param ausencia Is a Ausencia Object.
     * @throws NonexistentEntityException This is thrown when the object no longer exists in the DataBase.
     * This Method tryes to override an already existent object Ausencia in the DataBase with the same Id. It's an update.
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
     *
     * @param id It's an identificator for the Object on the DataBase.
     * @throws NonexistentEntityException This is thrown when the object no longer exists in the DataBase.
     * This method tryes to remove an existing object from the DataBase based on it's id. 
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
     *
     * @return Returns all Ausencia objects.
     */
    public List<Ausencia> findAusenciaEntities() {
        return this.findAusenciaEntities(true, -1, -1);
    }

    public List<Ausencia> findAusenciaEntities(int maxResults, int firstResult) {
        return this.findAusenciaEntities(false, maxResults, firstResult);
    }
    
    /**
     *
     * @param prof Um objeto professor / a professor object
     * @return Uma lista de ausências desse professor. / A list of ausencias of this professor object.
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
     *
     * @param id The identificator of the Ausencia Object you're looking for.
     * @return An Ausencia object, if was found. 
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

    public Ausencia findAusencia(String codigo) {
        List<Ausencia> ausencias = this.findAusenciaEntities();
        for (Ausencia ausencia : ausencias) {
            if (ausencia.getCodigo().equals(codigo)) {
                return ausencia;
            }
        }

        return null;
    }

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
