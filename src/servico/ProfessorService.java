/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import datamapper.AusenciaJpaController;
import datamapper.ProfessorJpaController;
import datamapper.exceptions.NonexistentEntityException;
import dominio.Aula;
import dominio.Ausencia;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import dominio.Professor;

/**
 *
 * @author Rick
 */
public class ProfessorService {

    private final ProfessorJpaController controller;

    private final AusenciaJpaController ausenciasRepository;

    public ProfessorService() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("pro_subPU");
        controller = new ProfessorJpaController(emf);
        ausenciasRepository = new AusenciaJpaController(emf);
    }

    public List<Professor> ListarProfessores() {
        // TODO letra minuscula do metodo

        List<Professor> professores = new LinkedList<>();
        List<Professor> modelos = new LinkedList<>();

        professores = controller.findProfessorEntities();

        for (Professor prof : professores) {
            Professor model = new Professor();
            model.setNome(prof.getNome());
            model.setId(prof.getId());
            modelos.add(model);
        }

        return modelos;
    }

    public List<Professor> listarProfessoresCompativeisComAusenteNoPeriodo(String codigoAusencia) throws NonexistentEntityException {

        Ausencia ausência = ausenciasRepository.findAusencia(new Long(codigoAusencia));

        List<Aula> aulasPerdidas = new LinkedList<>();
        aulasPerdidas.add(ausência.getAula());

        Professor professorAusente = ausência.getProfessor();

        List<Professor> todosProfessores = controller.findProfessorEntities();
        List<Ausencia> todasAsAusencias = ausenciasRepository.findAusenciaEntities();

        List<Professor> profsPossiveis = new ArrayList<>();

        for (Professor professor : todosProfessores) {
            if (professor.equals(professorAusente) || !professor.EhCompativelCom(aulasPerdidas)) {
                continue;
            }

            Boolean compatível = true;

            for (Ausencia ausenciaPreExistente : todasAsAusencias) {
                if (ausenciaPreExistente.getProfessorSubstituto() != null
                        && ausenciaPreExistente.getProfessorSubstituto().equals(professor)
                        && ausenciaPreExistente.getPeriodo().overlaps(ausência.getPeriodo())) {
                    compatível = false;
                    break;
                }
            }

            if (compatível) {
                Professor model = new Professor();

                model.setNome(professor.getNome());
                model.setId(professor.getId());

                profsPossiveis.add(model);
            }
        }

        return profsPossiveis;
    }

    public Professor obterProfessorPorNome(String nome) {

        Professor professor = controller.findProfessor(nome);

        if (professor == null) {
            return null;
        }

        Professor model = new Professor();

        model.setNome(professor.getNome());
        model.setId(professor.getId());

        return model;

    }

    public Professor obterProfessorPorUsername(String username) {
        Professor professor = controller.findProfessorPorUsername(username);

        if (professor == null) {
            return null;
        }

        Professor model = new Professor();

        model.setNome(professor.getNome());
        model.setId(professor.getId());

        return model;
    }

}
