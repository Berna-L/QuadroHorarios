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
import br.uff.id.bernardolopes.quadrohorarios.controller.model.RequestTurma;
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
    private DisciplinaDAO disciplinaDAO;

    public TurmaService() {
    }

    public void setTurmaDAO(TurmaDAO turmaDAO) {
        this.turmaDAO = turmaDAO;
    }

    public void setDisciplinaDAO(DisciplinaDAO disciplinaDAO) {
        this.disciplinaDAO = disciplinaDAO;
    }

    public List<Turma> getTurmas() {
        return turmaDAO.findAll();
    }

    public Turma getTurma(Long id) {
        return turmaDAO.findOne(id);
    }

    public Turma criarTurma(RequestTurma request) {
        if (request != null && request.isValid()) {
            return criarTurma(request.getCodigoTurma(), request.getAnoSemestre(), request.getCodigoDisciplina());
        } else {
            throw new IllegalArgumentException("Requisição inválida!");
        }
    }

    public Turma criarTurma(String codigoTurma, String anoSemestre, String codigoDisciplina) throws InstanceAlreadyExistsException {
        try {
            Disciplina d = disciplinaDAO.findByCodigo(codigoDisciplina).get(0);
            return criarTurma(codigoTurma, anoSemestre, d);
        } catch (IndexOutOfBoundsException ex) {
            throw new IllegalArgumentException("Disciplina não encontrada com código " + codigoDisciplina);
        }
    }

    public Turma criarTurma(String codigoTurma, String anoSemestre, Disciplina disciplina) throws InstanceAlreadyExistsException {
        if (turmaDAO.findByCodigoAndAnoSemestreAndDisciplina(codigoTurma, anoSemestre, disciplina).isEmpty()) { //Se já existe turma com código, não pode criar outra
            if (codigoTurma == null) {
                throw new IllegalArgumentException("Código da turma não pode ser nulo!");
            }
            if (anoSemestre == null) {
                throw new IllegalArgumentException("AnoSemestre não pode ser nulo!");
            }
            if (disciplina == null) {
                throw new IllegalArgumentException("Disciplina não pode ser nulo!");
            }
            Turma t = new Turma(codigoTurma, anoSemestre, disciplina);
            turmaDAO.save(t);
            return t;
        } else {
            throw new InstanceAlreadyExistsException();
        }

    }

    public Long getQuantidadeTurmasParaDisciplina(Long id) {
        Disciplina d = disciplinaDAO.findOne(id);
        if (d == null) {
            throw new IllegalArgumentException("Disciplina não encontrada!");
        }
        return turmaDAO.getQuantidadeTurmasForDisciplina(d);
    }
}
