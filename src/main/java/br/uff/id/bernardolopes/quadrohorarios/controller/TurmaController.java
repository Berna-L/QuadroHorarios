/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.util.RequestDisciplina;
import br.uff.id.bernardolopes.quadrohorarios.util.RequestTurma;
import java.net.URI;
import java.net.URISyntaxException;
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
    private TurmaDAO turmaDAO;
    
    @Autowired
    private DisciplinaDAO disciplinaDAO;
    
//    @Transactional
//    @PostMapping(path = "/turmas")
//    public void criarTurmaComObjetoDisciplina(String codigo, Disciplina disciplina){;
//        if (disciplina != null){
//            Turma t = new Turma(codigo, disciplina);
//            turmaDAO.save(t);
//        } else {
//            throw new IllegalArgumentException("Turma inválida!");
//        }
//    }
//    
//    @GetMapping(path = "/turmas")
//    public void getTurma()
//    
    @Transactional
    @PostMapping(path = "/turmas")
    public ResponseEntity<Turma> criarTurma(
            @RequestBody RequestTurma request, HttpServletResponse response){
        if (request.isValid()){
            Disciplina d = disciplinaDAO.findByCodigo(request.getCodigoDisciplina()).get(0);
            if (turmaDAO.findByCodigoAndDisciplina(request.getCodigoTurma(), d).isEmpty()){
                if (d == null){
                    return ResponseEntity.badRequest().body(null);
                }
                Turma t = new Turma(request.getCodigoTurma(), d);
                turmaDAO.save(t);
                try {
                    return ResponseEntity.created(new URI("/turmas/" + t.getId())).body(t);
                } catch (URISyntaxException ex) {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
                }
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
            }
        } else {
            return ResponseEntity.badRequest().body(null);
        }
    }
}
