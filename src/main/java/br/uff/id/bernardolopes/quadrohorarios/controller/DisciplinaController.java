/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.controller.model.RequestDisciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.service.DisciplinaService;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RestController
public class DisciplinaController {

    Logger logger = LoggerFactory.getLogger(DisciplinaController.class);
    @Autowired
    private DisciplinaService service;

    @GetMapping(path = "/disciplinas")
    public ResponseEntity<List<Disciplina>> getDisciplinas() {
        return ResponseEntity.ok().body(service.getDisciplinas());
    }

    @GetMapping(value = {"/disciplinas/{id}", "/disciplina/{id}"})
    public ResponseEntity<Disciplina> getDisciplina(@PathVariable Long id){
        return ResponseEntity.ok().body(service.getDisciplina(id));
    }
    
    @Transactional
    @PostMapping(path = "/disciplinas")
    public ResponseEntity<Disciplina> criarDisciplina(
            @RequestBody RequestDisciplina request, HttpServletResponse response) throws URISyntaxException {
        logger.info("TESTE");
        Disciplina d = service.criarDisciplina(request);
        logger.info("TESTE2");
        return ResponseEntity.created(new URI("/disciplinas/" + d.getId())).body(d);
    }
    
    
    
}
