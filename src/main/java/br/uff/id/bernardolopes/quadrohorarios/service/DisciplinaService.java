/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import java.net.URI;
import java.net.URISyntaxException;
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

    @Autowired
    private DisciplinaDAO disciplinaDAO;

    @Autowired
    private CursoDAO cursoDAO;

    public DisciplinaService() {
    }
    
    public Disciplina criarDisciplina(String codigoDisciplina, String nome, Curso curso) throws InstanceAlreadyExistsException {
        if (disciplinaDAO.findByCodigo(codigoDisciplina).isEmpty()) {
            if (codigoDisciplina == null){
                throw new IllegalArgumentException("Código da disciplina não pode ser nulo!");
            }
            if (nome == null){
                throw new IllegalArgumentException("Nome da disciplina não pode ser nulo!");
            }
            if (curso == null){
                throw new IllegalArgumentException("Curso não pode ser nulo!");
            }
            Disciplina d = new Disciplina(codigoDisciplina, nome, curso);
            disciplinaDAO.save(d);
            return d;
        } else {
            throw new InstanceAlreadyExistsException();
        }

    }

    public Disciplina criarDisciplina(String codigoDisciplina, String nome, Long codigoCurso) throws InstanceAlreadyExistsException {
        Curso curso = cursoDAO.findOne(codigoCurso);
        if (curso == null){
            throw new IllegalArgumentException("Curso não encontrado com código " + codigoCurso);
        }
        return criarDisciplina(codigoDisciplina, nome, curso);
    }
}
