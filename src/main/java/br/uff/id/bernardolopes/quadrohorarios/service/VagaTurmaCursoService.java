/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.VagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.model.unmanaged.RequestVagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.VagaTurmaCursoDAO;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Service
public class VagaTurmaCursoService {

    @Autowired
    private VagaTurmaCursoDAO vagaTurmaCursoDAO;

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private CursoDAO cursoDAO;

    public VagaTurmaCursoService() {
    }

    public void setVagaTurmaCursoDAO(VagaTurmaCursoDAO vagaTurmaCursoDAO) {
        this.vagaTurmaCursoDAO = vagaTurmaCursoDAO;
    }

    public VagaTurmaCurso criarVagaTurmaCurso(RequestVagaTurmaCurso request) {
        if (request.isValid()) {
            return criarVagaTurmaCurso(request.getIdTurma(), request.getIdCurso(), request.getVagas());
        } else {
            throw new IllegalArgumentException("Requisição inválida!");
        }
    }

    public VagaTurmaCurso criarVagaTurmaCurso(long idTurma, long idCurso, int vagas) {
        Turma turma = turmaDAO.getOne(idTurma);
        if (turma == null) {
            throw new IllegalArgumentException("Turma não encontrada!");
        }
        Curso curso = cursoDAO.getOne(idCurso);
        if (curso == null) {
            throw new IllegalArgumentException("Curso não encontrado!");
        }
        return criarVagaTurmaCurso(turma, curso, vagas);
    }

    public VagaTurmaCurso criarVagaTurmaCurso(Turma turma, Curso curso, int vagas) {
        if (vagaTurmaCursoDAO.findByTurmaAndCurso(turma, curso).isEmpty()){
            if (turma == null) {
                throw new IllegalArgumentException("Turma não pode ser nulo!");
            }
            if (curso == null) {
                throw new IllegalArgumentException("Curso não pode ser nulo!");
            }
            if (vagas < 1) {
                throw new IllegalArgumentException("Vagas precisa ser positivo!");
            }
            VagaTurmaCurso vtc = new VagaTurmaCurso(turma, curso, vagas);
            vagaTurmaCursoDAO.save(vtc);
            return vtc;
        }else {
            throw new InstanceAlreadyExistsException();
    }
}

public Map<Curso, Integer> getVagasPorCurso(Turma turma) {
        if (turma == null) {
            throw new IllegalArgumentException("Turma não pode ser nulo!");
        }
        List<VagaTurmaCurso> lista = vagaTurmaCursoDAO.findByTurma(turma);
        Map<Curso, Integer> relacaoVagas = new HashMap<>();
        for (VagaTurmaCurso entrada : lista) {
            relacaoVagas.put(entrada.getCurso(), entrada.getVagas());
        }
        return relacaoVagas;
    }

    public Map<Curso, Integer> fakeGetInscritosPorCurso(Turma turma) {
        //NOTHING IS REAL ANYMORE
        Map<Curso, Integer> relacaoVagas = getVagasPorCurso(turma);
        Map<Curso, Integer> relacaoInscritos = new HashMap<>();
        for (Curso curso : relacaoVagas.keySet()) {
            relacaoInscritos.put(curso, (int) Math.floor(Math.random() * relacaoVagas.get(curso).doubleValue()));
        }
        return relacaoInscritos;
    }

}
