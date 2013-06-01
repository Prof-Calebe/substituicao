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
    Alocacao_Efetuada(1),
    Alocacao_Cancelada(2),
    Alocacao_Pendente(3),
    Aulas_Canceladas(4);

    private int id;

    private EstadoAusencia(int c) {
      id = c;
    }

    public int getCode() {
      return id;
    }
};