/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.Columns;
import org.joda.time.DateTime;
import org.joda.time.Interval;

/**
 *
 * @author Leticia
 */
@Entity
public class Aula implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @Columns(columns={@Column(name="startTime"),@Column(name="endTime")})
    @Type(type="org.joda.time.contrib.hibernate.PersistentInterval")    
    private Interval periodo;
    
    private String nome;
    private int diaDaSemana;
    
    protected Aula(){
        
    }

    public Aula(String nome, int diaDaSemana, Interval periodo) {
        this.nome = nome;
        this.periodo = periodo;
        this.diaDaSemana = diaDaSemana;        
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
        //hash += (id != null ? id.hashCode() : 0);
        hash += periodo.hashCode() + diaDaSemana*31;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
//        if (!(object instanceof Aula)) {
//            return false;
//        }
//        Aula other = (Aula) object;
//        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
//            return false;
//        }
//        return true;
        
        Aula other = (Aula) object;
        if(this.diaDaSemana == other.diaDaSemana && this.periodo.equals(other.periodo) )
            return true;
        else
            return false; 
    }

    @Override
    public String toString() {
        return "Dominio.Aula[ id=" + id + " ]";
    }

    /**
     * @return the periodo
     */
    public Interval getPeriodo() {
        return periodo;
    }

    /**
     * @return the diaDaSemana
     */
    public int getDiaDaSemana() {
        return diaDaSemana;
    }
    
    public String getNome() {
        return nome;
    }

    public boolean bateCom(Aula outraAula) {
        
        if(this.diaDaSemana != outraAula.getDiaDaSemana()){
            return false;
        }else if (this.bateHorarioCom(outraAula.getPeriodo())){
            return true;
        }
        
        return false;
    }
    
    public boolean bateHorarioCom(Interval outroPeriodo){
        
        int anoIntervalo = outroPeriodo.getStart().getYear();
        int mesIntervalo = outroPeriodo.getStart().getMonthOfYear();
        int diaIntervalo = outroPeriodo.getStart().getDayOfMonth();
        
        int horaInicio = this.getPeriodo().getStart().getHourOfDay();
        int minutoInicio = this.getPeriodo().getStart().getMinuteOfHour();
        
        int horaFim = this.getPeriodo().getEnd().getHourOfDay();
        int minutoFim = this.getPeriodo().getEnd().getMinuteOfHour();
        
        
        DateTime tempInicio = new DateTime(anoIntervalo, mesIntervalo, diaIntervalo, horaInicio, minutoInicio);
        DateTime tempFim = new DateTime(anoIntervalo, mesIntervalo, diaIntervalo, horaFim, minutoFim);
        
        Interval intervaloTemp = new Interval(tempInicio, tempFim);
        
        return intervaloTemp.overlaps(outroPeriodo);
    }
    
}

