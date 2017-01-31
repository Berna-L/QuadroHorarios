/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.id.uff.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RestController
public class TurmaController {
    
    @Autowired
    private TurmaDAO turmaDAO;
    
    @Autowired
    private DisciplinaDAO disciplinaDAO;
    
    @PostMapping(path = "/turmas")
    public void criarTurma(String codigo, Disciplina disciplina){
        Turma t = new Turma(codigo, disciplina);
        turmaDAO.save(t);
    }
    
    @PostMapping(path = "/turmas")
    public void criarTurma(String codigoTurma, String codigoDisciplina){
        Disciplina d = disciplinaDAO.findOne(codigoDisciplina);
        this.criarTurma(codigoTurma, d);
    }
}
