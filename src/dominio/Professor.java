/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import dominio.Aula;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ElementCollection;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
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
    private List<Aula> grade;
    
    @ManyToMany(targetEntity=Ausencia.class,mappedBy="indicacoesSubstituto",fetch = FetchType.EAGER)
    private List<Ausencia> ausencias;
    
    
    protected Professor(){}

    public Professor(String nome, String username) {
        this.nome = nome;
        this.grade = new LinkedList<Aula>();
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
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
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
                if(minhaAula.bateCom(aulaIntruso) || aulaIntruso.bateCom(minhaAula) ){
                    ehCompativel = false;
                    break;
                }
            } 
        }
        return ehCompativel;

    }

    public List<Aula> verificarAulasPerdidasNoPeriodo(Interval periodoAusencia) {
              
        DateTime data = periodoAusencia.getStart();
        DateTime finalAusencia = periodoAusencia.getEnd();
        
        List<Aula> aulasComprometidas = new LinkedList<Aula>();
        
        
        if(Hours.hoursBetween(data, finalAusencia).isGreaterThan(Hours.hours(24)) ||
                Hours.hoursBetween(data, finalAusencia).equals(Hours.hours(24))){
            
            
            Days dias = Days.daysBetween(data, finalAusencia);
            
            
            while(dias.isGreaterThan(Days.days(0))){
                
                for(Aula aula : this.grade){
                    if(aula.getDiaDaSemana() == data.getDayOfWeek() &&
                            !aulasComprometidas.contains(aula)){
                        aulasComprometidas.add(aula);
                    }
                }
                
                dias = dias.minus(1);
                
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
   
//    private int diasEntre(Periodo periodo){
//        
//        Calendar comeco = periodo.getLimiteInferior();
//        Calendar fim = periodo.getLimiteSuperior();
//        
//        Calendar data = (Calendar)comeco.clone();
//        
//        int diasEntre = 0;
//        
//        if(comeco.equals(fim)){
//            diasEntre = 2; //Contando os extremos
//        }else{
//            diasEntre = 1;
//        }
//        
//        while(data.before(fim)){
//            data.add(Calendar.DAY_OF_MONTH, 1);
//            diasEntre++;
//        }
//        
//        return diasEntre;
//    }

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
}
