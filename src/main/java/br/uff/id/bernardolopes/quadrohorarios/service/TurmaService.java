/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.VagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.VagaTurmaCursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.util.RequestTurma;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Service
public class TurmaService {

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private VagaTurmaCursoDAO vagaTurmaCursoDAO;

    @Autowired
    private DisciplinaDAO disciplinaDAO;

    public TurmaService() {
    }

    public void setTurmaDAO(TurmaDAO turmaDAO) {
        this.turmaDAO = turmaDAO;
    }

    public void setVagaTurmaCursoDAO(VagaTurmaCursoDAO vagaTurmaCursoDAO) {
        this.vagaTurmaCursoDAO = vagaTurmaCursoDAO;
    }

    public void setDisciplinaDAO(DisciplinaDAO disciplinaDAO) {
        this.disciplinaDAO = disciplinaDAO;
    }

    public List<Turma> getTurmas() {
        return turmaDAO.findAll();
    }
    
    public Turma getTurma(long id) {
        return turmaDAO.findOne(id);
    }

    public Turma criarTurma(RequestTurma request) {
        if (request.isValid()) {
            return criarTurma(request.getCodigoTurma(), request.getAnosemestre(), request.getCodigoDisciplina());
        } else {
            throw new IllegalArgumentException("Requisição inválida!");
        }
    }

    public Turma criarTurma(String codigoTurma, String anosemestre, String codigoDisciplina) throws InstanceAlreadyExistsException {
        try {
            Disciplina d = disciplinaDAO.findByCodigo(codigoDisciplina).get(0);
            return criarTurma(codigoTurma, anosemestre, d);
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Disciplina não encontrada com código " + codigoDisciplina);
        }
    }

    public Turma criarTurma(String codigoTurma, String anosemestre, Disciplina disciplina) throws InstanceAlreadyExistsException {
        if (turmaDAO.findByCodigoAndAnosemestreAndDisciplina(codigoTurma, anosemestre, disciplina).isEmpty()) { //Se já existe turma com código, não pode criar outra
            if (codigoTurma == null) {
                throw new IllegalArgumentException("Código da turma não pode ser nulo!");
            }
            if (anosemestre == null) {
                throw new IllegalArgumentException("Anosemestre não pode ser nulo!");
            }
            if (disciplina == null) {
                throw new IllegalArgumentException("Disciplina não pode ser nulo!");
            }
            Turma t = new Turma(codigoTurma, anosemestre, disciplina);
            turmaDAO.save(t);
            return t;
        } else {
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
