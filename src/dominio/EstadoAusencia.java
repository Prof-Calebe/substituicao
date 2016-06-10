/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dominio;

/**
 *
 * @author Thiago Lima
 */
public enum EstadoAusencia {
    Alocacao_Efetuada   (1, "Alocação efetuada"),
    Ausencia_Cancelada  (2, "Ausência cancelada"),
    Alocacao_Pendente   (3, "Alocação pendente"),
    Aulas_Canceladas    (4, "Aulas canceladas"),
    Alocacao_Confirmada (5, "Alocação confirmada");

    private final int code;
    private final String descricao;

    private EstadoAusencia(int code, String descricao) {
        this.code = code;
        this.descricao = descricao;
    }

    public int getCode() {
        return code;
    }

    public String getDescricao() {
        return descricao;
    }
    
    
};