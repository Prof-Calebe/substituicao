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

    public AusenciaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Ausencia ausencia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(ausencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Ausencia ausencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ausencia = em.merge(ausencia);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = ausencia.getId();
                if (findAusencia(id) == null) {
                    throw new NonexistentEntityException("The ausencia with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Ausencia ausencia;
            try {
                ausencia = em.getReference(Ausencia.class, id);
                ausencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The ausencia with id " + id + " no longer exists.", enfe);
            }
            em.remove(ausencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Ausencia> findAusenciaEntities() {
        return findAusenciaEntities(true, -1, -1);
    }

    public List<Ausencia> findAusenciaEntities(int maxResults, int firstResult) {
        return findAusenciaEntities(false, maxResults, firstResult);
    }
    
    public List<Ausencia> listAusenciasPorProfessor(Professor prof){
        List<Ausencia> ausencias = this.findAusenciaEntities();
        
        List<Ausencia> ausenciasComProfessor = new ArrayList<Ausencia>();
        
        for(Ausencia ausencia : ausencias){
            
            if(ausencia.getProfessor().equals(prof)){
                ausenciasComProfessor.add(ausencia);
            }
            
        }
        
        return ausenciasComProfessor;
    }
    
    public List<Ausencia> listAusenciasPorIndicacaoDeSubstituto(Professor professor){
        List<Ausencia> ausencias = this.findAusenciaEntities();
        
        List<Ausencia> ausenciasComProfessor = new ArrayList<Ausencia>();
        
        for(Ausencia ausencia : ausencias){
            
            if(ausencia.getIndicacoesSubstitutos().contains(professor)){
                ausenciasComProfessor.add(ausencia);
            }
            
        }
        
        return ausenciasComProfessor;
    }
    
    public List<Ausencia> listAusenciasPorSubstituto(Professor professor){
        List<Ausencia> ausencias = this.findAusenciaEntities();
        
        List<Ausencia> ausenciasComProfessor = new ArrayList<Ausencia>();
        
        for(Ausencia ausencia : ausencias){
            
            if(ausencia.getProfessorSubstituto().equals(professor)){
                ausenciasComProfessor.add(ausencia);
            }
            
        }
        
        return ausenciasComProfessor;
    }

    private List<Ausencia> findAusenciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
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

    public Ausencia findAusencia(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Ausencia.class, id);
        } finally {
            em.close();
        }
    }
    
    public Ausencia findAusencia(String codigo){
    List<Ausencia> ausencias = this.findAusenciaEntities();
    for(Ausencia ausencia : ausencias){
        if(ausencia.getCodigo().equals(codigo))
            return ausencia;
    }
        
        return null;
    }
    

    public int getAusenciaCount() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select count(o) from Ausencia as o");
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
