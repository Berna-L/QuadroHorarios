/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.util.RespostaDisciplina;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
    
//    @Transactional
//    @PostMapping(path = "/disciplinas")
    public void criarDisciplinaComObjetoCurso(String codigo, String nome, Curso curso){
        if (curso != null){
            Disciplina d = new Disciplina(codigo, nome, curso);
            disciplinaDAO.save(d);
        } else {
            throw new IllegalArgumentException("Curso inválido!");
        }
    }
    
    @Transactional
    @ResponseBody
    @RequestMapping(path = "/disciplinas", method = RequestMethod.POST)
    public void criarDisciplinaComCodigoCurso(
            @RequestBody RespostaDisciplina resposta){
//        System.out.println("codigoDisciplina" + codigoDisciplina);
//        System.out.println("nome" + nome);
//        System.out.println("c" + codigoCurso);
          System.out.println(resposta);
//        Curso c = cursoDAO.findOne(resposta.getCodigoCurso());
//        this.criarDisciplinaComObjetoCurso(resposta.getCodigoDisciplina(), resposta.getNome(), c);
    }
}
