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
import br.uff.id.bernardolopes.quadrohorarios.util.RequestDisciplina;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
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

    public void setDisciplinaDAO(DisciplinaDAO disciplinaDAO) {
        this.disciplinaDAO = disciplinaDAO;
    }

    public void setCursoDAO(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }
    
    public List<Disciplina> getDisciplinas(){
        return disciplinaDAO.findAll();
    }
    
    public Disciplina getDisciplina(long id){
        return disciplinaDAO.findOne(id);
    }
    
    public Disciplina criarDisciplina(RequestDisciplina request){
        if (request.isValid()){
            return criarDisciplina(request.getCodigoDisciplina(), request.getNome(), request.getCodigoCurso());
        } else {
            throw new IllegalArgumentException("Requisição inválida!");
        }
    }

    public Disciplina criarDisciplina(String codigoDisciplina, String nome, Long codigoCurso) throws InstanceAlreadyExistsException {
        Curso curso = cursoDAO.findOne(codigoCurso);
        if (curso == null){
            throw new IllegalArgumentException("Curso não encontrado com código " + codigoCurso);
        }
        return criarDisciplina(codigoDisciplina, nome, curso);
    }
    
    public Disciplina criarDisciplina(String codigoDisciplina, String nome, Curso curso) throws InstanceAlreadyExistsException {
        if (disciplinaDAO.findByCodigo(codigoDisciplina).isEmpty()) { //Se já existe disciplina com código, não pode criar outra
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

}
