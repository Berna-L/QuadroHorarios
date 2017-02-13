/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.uff.id.bernardolopes.quadrohorarios.controller.DisciplinaController;
import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.controller.model.RequestDisciplina;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Service
public class DisciplinaService {

    Logger logger = LoggerFactory.getLogger(DisciplinaController.class);

    @Autowired
    private DisciplinaDAO disciplinaDAO;

    @Autowired
    private CursoDAO cursoDAO;

    public DisciplinaService() {
    }

    public void setDisciplinaDAO(DisciplinaDAO disciplinaDAO) {
        this.disciplinaDAO = disciplinaDAO;
    }

    public void setCursoDAO(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

    public List<Disciplina> getDisciplinas() {
        return disciplinaDAO.findAll();
    }

    public Disciplina getDisciplina(Long id) {
        return disciplinaDAO.findOne(id);
    }

    public Disciplina criarDisciplina(RequestDisciplina request) {
        if (request != null && request.isValid()) {
            return criarDisciplina(Optional.of(request.getCodigoDisciplina()), Optional.of(request.getNome()), request.getCodigoCurso());
        } else {
            throw new IllegalArgumentException("Requisição inválida!");
        }
    }

    public Disciplina criarDisciplina(Optional<String> codigoDisciplina, Optional<String> nome, Long codigoCurso) throws InstanceAlreadyExistsException {
        Optional cursoOpt = Optional.ofNullable(cursoDAO.findOne(codigoCurso));
//        logger.info("Curso {}", curso);
//        logger.info("Curso. {}", curso.getCodigo());
        return criarDisciplina(codigoDisciplina, nome, cursoOpt);
//        logger.info("Curso! {}");
    }

    public Disciplina criarDisciplina(Optional<String> codigoDisciplina, Optional<String> nome, Optional<Curso> curso) throws InstanceAlreadyExistsException {
        String nomeNaoOpt = nome.orElseThrow(() -> new IllegalArgumentException("Nome da disciplina não pode ser nulo!"));
        Curso cursoNaoOpt = curso.orElseThrow(() -> new IllegalArgumentException("Curso não pode ser nulo!"));
        String codigoDisciplinaNaoOpt = codigoDisciplina.orElseThrow(() -> new IllegalArgumentException("Código da disciplina não pode ser nulo"));
        if (disciplinaDAO.findByCodigo(codigoDisciplinaNaoOpt).isEmpty()) { //Se já existe disciplina com código, não pode criar outra
            Disciplina d = new Disciplina(codigoDisciplinaNaoOpt, nomeNaoOpt, cursoNaoOpt);
            disciplinaDAO.save(d);
            return d;
        } else {
            throw new InstanceAlreadyExistsException();
        }
    }

}
