/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import org.hibernate.annotations.Columns;
import org.hibernate.annotations.Type;
import org.joda.time.Interval;

/**
 *
 * @author Leticia
 */
@Entity
public class Ausencia implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @ManyToOne
    private Professor professor;
    
    @Columns(columns={@Column(name="startTime"),@Column(name="endTime")})
    @Type(type="org.joda.time.contrib.hibernate.PersistentInterval")  
    private Interval periodo;
    
    private String motivo;
    
    private String motivoRejeicao;
    
    @ManyToOne
    private Professor indicacaoSubstituto;
    
    private EstadoAusencia estado;
    
    private String codigo;
    
    //blame Hibernate
    protected Ausencia(){
    
    }

    public Ausencia(String codigo, Interval periodo, Professor professor, String motivo) {
        this.codigo = codigo;
        this.periodo = periodo;
        this.professor = professor;
        this.motivo = motivo;
        this.motivoRejeicao = null;
        this.indicacaoSubstituto = null;
        this.estado = EstadoAusencia.Alocacao_Pendente;
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
        if (!(object instanceof Ausencia)) {
            return false;
        }
        Ausencia other = (Ausencia) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Dominio.Ausencia[ id=" + id + " ]";
    }

    public Professor getProfessor() {
        return professor;
    }
    
    public void setProfessor(Professor professor) {
        this.professor = professor;
    }

    public Interval getPeriodo() {
        return periodo;
    }
    
    public void setPeriodo(Interval periodo) {
        this.periodo = periodo;
    }

    public Object getMotivo() {
        return motivo;
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    
    public String getMotivoRejeicao() {
        return motivoRejeicao;
    }

    public void setMotivoRejeicao(String motivo) {
        this.motivoRejeicao = motivo;
    }
    
    public EstadoAusencia getEstado() {
        return this.estado;
    }
    
    /**
     * @return the indicacaoSubstituto
     */
    public Professor getIndicacaoSubstituto() {
        return indicacaoSubstituto;
    }

    public void indicarSubstituto(Professor professor) {
        
        if (professor == this.professor){
            throw new IllegalStateException("O professor substituto não deve ser o mesmo que o professor se ausentando.");
        }
        this.indicacaoSubstituto = professor;
    }

    /**
     * @return the codigo
     */
    public String getCodigo() {
        return codigo;
    }
    
}