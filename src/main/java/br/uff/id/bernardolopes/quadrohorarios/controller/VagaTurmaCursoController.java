/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.service.VagaTurmaCursoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bernardolopes at id.uff.br
 */

@RestController
public class VagaTurmaCursoController {
    
    @Autowired
    private VagaTurmaCursoService service;
    
    @GetMapping(path = "/vagaturmacurso")
    public ResponseEntity<List<Turma>> getTurmasPorCursoEAnoSemestre(long idCurso, String anoSemestre){
        List<Turma> resultado = service.getTurmasParaCursoEAnoSemestre(idCurso, anoSemestre);
        return ResponseEntity.ok(resultado);
    }
    
}
