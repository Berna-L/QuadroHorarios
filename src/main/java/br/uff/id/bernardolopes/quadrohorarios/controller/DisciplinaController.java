/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.service.DisciplinaService;
import br.uff.id.bernardolopes.quadrohorarios.model.unmanaged.RequestDisciplina;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RestController
public class DisciplinaController {

    @Autowired
    private DisciplinaService service;

    @GetMapping(path = "/disciplinas")
    public ResponseEntity<List<Disciplina>> getDisciplinas() {
        return ResponseEntity.ok().body(service.getDisciplinas());
    }

    @GetMapping(path = {"/disciplinas/{id}", "/disciplina/{id}"})
    public ResponseEntity<Disciplina> getDisciplina(long id){
        return ResponseEntity.ok().body(service.getDisciplina(id));
    }
    
    @Transactional
    @PostMapping(path = "/disciplinas")
    public ResponseEntity<Disciplina> criarDisciplina(
            @RequestBody RequestDisciplina request, HttpServletResponse response) throws URISyntaxException {
        Disciplina d = service.criarDisciplina(request);
        return ResponseEntity.created(new URI("/disciplinas/" + d.getId())).body(d);
    }
    
    
    
}
