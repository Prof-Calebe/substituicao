/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
    
    @ManyToOne
    private Aula aula;
    
    @Columns(columns={@Column(name="startTime"),@Column(name="endTime")})
    @Type(type="org.joda.time.contrib.hibernate.PersistentInterval")  
    private Interval periodo;
    
    private String motivo;
    
    private String motivoRejeicao;
    
    @ManyToOne
    private Professor professorSubstituto;
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name="ausencia_professorSubstituto")
    private List<Professor> indicacoesSubstituto;
    
    private EstadoAusencia estado;
    
    private String codigo;
    
    //blame Hibernate
    protected Ausencia(){
    
    }

    public Ausencia(String codigo, Interval periodo, Professor professor, String motivo, Aula aula) {
        this.codigo = codigo;
        this.periodo = periodo;
        this.professor = professor;
        this.motivo = motivo;
        this.motivoRejeicao = null;
        //this.indicacaoSubstituto = null;
        this.indicacoesSubstituto = new ArrayList<Professor>();
        this.estado = EstadoAusencia.Alocacao_Pendente;       
        this.aula = aula;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    
    public void setCodigo(String codigo)
    {
        this.codigo = codigo;
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
    
    public Aula getAula() {
        return aula;
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
    public List<Professor> getIndicacoesSubstitutos() {
        return indicacoesSubstituto;
    }

    public void indicarSubstituto(Professor professor) {
        
        if (professor == this.professor){
            throw new IllegalStateException("O professor substituto não deve ser o mesmo que o professor se ausentando.");
        }
        //this.indicacaoSubstituto = professor;
        
        this.indicacoesSubstituto.add(professor);
    }
    
    public void retirarSubstituto(Professor professor){
        
        if(!this.indicacoesSubstituto.contains(professor)){
            throw new IllegalStateException("O professor substituto não existe nesta notificação de ausência.");
        }
        
        this.indicacoesSubstituto.remove(professor);
    }

    /**
     * @return the codigothis
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * @return the professorSubstituto
     */
    public Professor getProfessorSubstituto() {
        return professorSubstituto;
    }

    /**
     * @param professorSubstituto the professorSubstituto to set
     */
    public void setProfessorSubstituto(Professor professorSubstituto) {
        if (professorSubstituto == this.professor){
            throw new IllegalStateException("O professor substituto não deve ser o mesmo que o professor se ausentando.");
        }
        
        this.professorSubstituto = professorSubstituto;
        this.definirComoAlocado();
    }

    public void cancelarAusencia() {
        
        this.estado = EstadoAusencia.Ausencia_Cancelada;
        this.professorSubstituto = null;
    }
    
    public void cancelarAulas(){
        
        this.estado = EstadoAusencia.Aulas_Canceladas;
        this.professorSubstituto = null;
        
    }
    
    public void definirComoAlocado(){
        this.estado = EstadoAusencia.Alocacao_Efetuada;
    }
    
    public void confirmar() {
        if(this.estado != EstadoAusencia.Alocacao_Efetuada)
        {
            throw new IllegalStateException();
        }
        
        this.estado = EstadoAusencia.Alocacao_Confirmada;
    }
    
    public void recusar() {
        if(this.estado != EstadoAusencia.Alocacao_Efetuada)
        {
            throw new IllegalStateException();
        }
        
        this.estado = EstadoAusencia.Alocacao_Pendente;
        this.professorSubstituto = null;
    }
    
    
   
    
}