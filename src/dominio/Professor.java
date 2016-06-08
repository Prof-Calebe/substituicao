/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import dominio.Aula;
import java.io.Serializable;
import java.security.InvalidParameterException;
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

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }
    
    public List<Ausencia> gerarAusencias(Interval periodoDeAusencia, String motivo)
    {
        if(motivo.equals(""))
            throw new InvalidParameterException();
        
        List<Ausencia> ausencias = new LinkedList<Ausencia>();
        
        Interval primeiroDia = null;
        Interval últimoDia = null;
        Interval diasCompletos = null;
        
        DateTime inícioAusência = periodoDeAusencia.getStart();
        DateTime finalAusência = periodoDeAusencia.getEnd();
        
        Boolean múltiplosDias = true;
        
        DateTime finalDePrimeiroDiaDeAusência = new DateTime(inícioAusência.getYear(),inícioAusência.getMonthOfYear(),inícioAusência.getDayOfMonth(),23,59);
        if(finalDePrimeiroDiaDeAusência.isAfter(finalAusência) || finalDePrimeiroDiaDeAusência.equals(finalAusência))
        {
            finalDePrimeiroDiaDeAusência = finalAusência;
            múltiplosDias = false;
        }
        
        primeiroDia = new Interval(inícioAusência, finalDePrimeiroDiaDeAusência);
        
        if(múltiplosDias)
        {
            DateTime inícioDoÚltimoDiaDeAusência = new DateTime(finalAusência.getYear(),finalAusência.getMonthOfYear(),finalAusência.getDayOfMonth(),00,00);
            últimoDia = new Interval(inícioDoÚltimoDiaDeAusência,finalAusência);
            
            if(inícioDoÚltimoDiaDeAusência.isAfter(finalDePrimeiroDiaDeAusência.plusMinutes(1)))
            {
                diasCompletos = new Interval(finalDePrimeiroDiaDeAusência.plusMinutes(1), inícioDoÚltimoDiaDeAusência.plusMinutes(-1));
            }
        }
        
        for(Aula aulaPedida : this.getGrade())
        {                    
            Avaliar(aulaPedida, primeiroDia, motivo, ausencias);
            
            if(últimoDia != null)
            {
                Avaliar(aulaPedida, últimoDia, motivo, ausencias);
            }
            
            if(diasCompletos != null)
            {
                DateTime inícioDiasCompletos = diasCompletos.getStart();
                DateTime fimDiascompletos = diasCompletos.getEnd();
                DateTime inícioDoÚltimoDiaCompleto = new DateTime(fimDiascompletos.getYear(),fimDiascompletos.getMonthOfYear(),fimDiascompletos.getDayOfMonth(),00,01);
                DateTime diaAtual = inícioDiasCompletos;
                while(diaAtual.isBefore(inícioDoÚltimoDiaCompleto))
                {
                    DateTime fimDoDiaAtual = new DateTime(diaAtual.getYear(),diaAtual.getMonthOfYear(),diaAtual.getDayOfMonth(),23,59);
                    Interval diaAtualCompleto = new Interval(diaAtual,fimDoDiaAtual);
                    
                    Avaliar(aulaPedida, diaAtualCompleto, motivo, ausencias);
                                    
                    diaAtual = diaAtual.plusDays(1);
                }                
            }            
        }
        
        return ausencias;
    }

    private void Avaliar(Aula aulaPedida, Interval primeiroDia, String motivo, List<Ausencia> ausencias) {
        if(aulaPedida.getDiaDaSemana() == primeiroDia.getStart().getDayOfWeek())
        {
            if(aulaPedida.bateHorarioCom(primeiroDia))
            {
                int anoAusência = primeiroDia.getStart().getYear();
                int mêsAusência = primeiroDia.getStart().getMonthOfYear();
                int diaAusência = primeiroDia.getStart().getDayOfMonth();
                int inícioHoraAula = aulaPedida.getPeriodo().getStart().getHourOfDay();
                int inícioMinutoAula = aulaPedida.getPeriodo().getStart().getMinuteOfHour();
                int finalHoraAula = aulaPedida.getPeriodo().getEnd().getHourOfDay();
                int finalMinutoAula = aulaPedida.getPeriodo().getEnd().getMinuteOfHour();
                
                DateTime inícioAusênciaAula = new DateTime(anoAusência,mêsAusência,diaAusência,inícioHoraAula,inícioMinutoAula);
                DateTime finalAusênciaAula = new DateTime(anoAusência,mêsAusência,diaAusência,finalHoraAula,finalMinutoAula);
                Interval períodoAusência = new Interval(inícioAusênciaAula,finalAusênciaAula);
                Ausencia ausência = new Ausencia("0", períodoAusência, this, motivo, aulaPedida);
                ausencias.add(ausência);
            }
        }
    }
    
}
