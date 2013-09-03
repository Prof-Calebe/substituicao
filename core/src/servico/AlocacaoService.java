/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.AusenciaJpaController;
import dominio.Ausencia;
import modelo.AusenciaModel;
import java.util.List;

/**
 *
 * @author Thiago Lima
 */
public class AlocacaoService {
    private List<AusenciaModel> listaDeAusencias = null;
    private List<Ausencia> listaDeAusenciaDummy = null;
    private AusenciaJpaController ausenciaJpaController;
    
//    public List<AusenciaModel> ListarAusencias() {
//        listaDeAusenciaDummy = ausenciaJpaController.findAusenciaEntities();
//        
//        for (Ausencia ausencia : listaDeAusenciaDummy) {
//            AusenciaModel model = new AusenciaModel();
//            model.id = ausencia.getId();
//            model.professorAusente = ausencia.getProfessor();
//            model.dataInicio = ausencia.getPeriodo().getLimiteInferior();
//            model.dataFim = ausencia.getPeriodo().getLimiteSuperior();
//            model.professorSubstituto = ausencia.getIndicacaoSubstituto();
//            model.estado = ausencia.getEstado();
//            listaDeAusencias.add(model);
//        }
//        
//        return listaDeAusencias;
//    }
}