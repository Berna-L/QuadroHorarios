/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.service.TurmaService;
import br.uff.id.bernardolopes.quadrohorarios.util.RequestDisciplina;
import br.uff.id.bernardolopes.quadrohorarios.util.RequestTurma;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RestController
public class TurmaController {

    @Autowired
    private TurmaService service;

    @GetMapping(path = "/turmas")
    public ResponseEntity<List<Turma>> getTurmas() {
        return ResponseEntity.ok().body(service.getTurmas());
    }

    @Transactional
    @PostMapping(path = "/turmas")
    public ResponseEntity<Turma> criarTurma(
            @RequestBody RequestTurma request, HttpServletResponse response) throws URISyntaxException {
        Turma t = service.criarTurma(request);
        return ResponseEntity.created(new URI("/turmas/" + t.getId())).body(t);
    }
}
