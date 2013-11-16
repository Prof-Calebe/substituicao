/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import dominio.Aula;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Interval;

/**
 *
 * @author Leticia
 */
@Entity
public class Professor implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    private String nome;
    
    private String username;
    
    @OneToMany
    @LazyCollection(LazyCollectionOption.FALSE) 
    private List<Aula> grade;
    
    @ManyToMany(targetEntity=Ausencia.class,mappedBy="indicacoesSubstituto",fetch = FetchType.EAGER)
    private List<Ausencia> ausencias;
    
    
    protected Professor(){}

    public Professor(String nome, String username) {
        this.nome = nome;
        this.grade = new ArrayList<Aula>();
        this.username = username;
        
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
        if (!(object instanceof Professor)) {
            return false;
        }
        Professor other = (Professor) object;
        
        if(this.id == null)
        {
            if(other.id == null)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            if(other.id == null)
            {
                return false;
            }
            
            return this.id.equals(other.id);
        }
    }

    @Override
    public String toString() {
        return "Dominio.Professor[ id=" + id + " ]";
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @return the grade
     */
    public List<Aula> getGrade() {
        return grade;
    }

    public void adicionarAula(Aula aula) {
        
        if(aula == null){
            throw new IllegalStateException("Aula inválida.");
        }
        if(grade.contains(aula)){
            throw new IllegalStateException("Professor já contem esta aula em sua grade");
        }
        
        grade.add(aula);
        
    }

    public boolean  EhCompativelCom(List<Aula> aulas) {
        
//        Collections.sort(this.grade, new AulaComparator());
        
        boolean ehCompativel = true;

        for(Aula aulaIntruso : aulas){
            for(Aula minhaAula : this.getGrade()) {
                if(minhaAula.bateCom(aulaIntruso)){
                    ehCompativel = false;
                    break;
                }
            } 
        }
        return ehCompativel;

    }

    public List<Aula> verificarAulasPerdidasNoPeriodo(Interval periodoAusencia) {
              
        DateTime inicioAusencia = periodoAusencia.getStart();
        DateTime finalAusencia = periodoAusencia.getEnd();
        
        List<Aula> aulasComprometidas = new LinkedList<Aula>();
                        
        if(Hours.hoursBetween(inicioAusencia, finalAusencia).isGreaterThan(Hours.hours(23))){            
            
            Days dias = Days.daysBetween(inicioAusencia, finalAusencia);
            
            
            while(dias.isGreaterThan(Days.days(0))){
                
                for(Aula aula : this.grade){
                    if(aula.getDiaDaSemana() == inicioAusencia.getDayOfWeek())
                    {
                        //TODO: Provavelmente existe um bug quando se declara uma ausência de mais de 1 semana. Na verdade, esse método deveria reportar cada aula com uma data de perda específica. Ou então, deveria haver N períodos de ausência unitários.
                        if(!aulasComprometidas.contains(aula))
                        {
                            aulasComprometidas.add(aula);
                        }
                    }
                    
                    /*if(aula.getDiaDaSemana() == inicioAusencia.getDayOfWeek() &&
                            !aulasComprometidas.contains(aula)){
                        aulasComprometidas.add(aula);
                    }*/
                }
                
                dias = dias.minus(1);
                inicioAusencia = inicioAusencia.plusDays(1);
                
            }
            
        }else{ //menos de 24 horas de ausência
            for(Aula aula : this.grade){
                if(  aula.getDiaDaSemana() == periodoAusencia.getStart().getDayOfWeek() &&
                        aula.bateHorarioCom(periodoAusencia) && 
                        !aulasComprometidas.contains(aula) ){
                    aulasComprometidas.add(aula);
                }
            }
        }
              
        return aulasComprometidas;
    }
   
    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
}
