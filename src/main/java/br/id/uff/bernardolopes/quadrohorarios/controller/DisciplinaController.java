/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.id.uff.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bernardolopes at id.uff.br
 */

@RestController
public class DisciplinaController {
    
    @Autowired
    private DisciplinaDAO disciplinaDAO;
    
    @Autowired
    private CursoDAO cursoDAO;
    
    @Transactional
    @PostMapping(path = "/disciplinas")
    public void criarDisciplina(String codigo, String nome, Curso curso){
        if (curso != null){
            Disciplina d = new Disciplina(codigo, nome, curso);
            disciplinaDAO.save(d);
        } else {
            throw new IllegalArgumentException("Curso inválido!");
        }
    }
    
    @Transactional
    @PostMapping(path = "/disciplinas")
    public void criarDisciplina(String codigoDisciplina, String nome, String codigoCurso){
        Curso c = cursoDAO.findOne(codigoCurso);
        this.criarDisciplina(codigoDisciplina, nome, c);
    }
}
