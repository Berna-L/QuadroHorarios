/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.controller.model.ResponseVagasInscritos;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.service.VagaTurmaCursoService;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RestController
public class VagaTurmaCursoController {

    @Autowired
    private VagaTurmaCursoService service;

    @GetMapping(path = "/turmacurso")
    public ResponseEntity<List<Turma>> getTurmasPorCursoEAnoSemestre(@RequestParam(name = "curso") Long codigoCurso, String anoSemestre) {
        List<Turma> resultado = service.getTurmasParaCursoEAnoSemestre(codigoCurso, anoSemestre);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping(path = "/turmas/porCurso")
    public ResponseEntity<Map<Long, Long>> getQuantidadeTurmasPorCurso() {
        Map<Long, Long> resultado = service.getQuantidadeTurmasPorCurso();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping(path = "/turmas/porAnoSemestre")
    public ResponseEntity<Map<String, Long>> getQuantidadeTurmasPorAnoSemestre() {
        Map<String, Long> resultado = service.getQuantidadeTurmasPorAnoSemestre();
        return ResponseEntity.ok(resultado);
    }

    @GetMapping(value = "/turmas/vagas/porAnoSemestre/paraDisciplina/{id}")
    public ResponseEntity<Map<String, Long>> getQuantidadeVagasPorAnoSemestreParaDisciplina(@PathVariable Long id) {
        Map<String, Long> resultado = service.getQuantidadeVagasPorAnoSemestreParaDisciplina(id);
        return ResponseEntity.ok(resultado);
    }
    
    @GetMapping(value = "/turmas/vagasInscritos/{id}")
    public ResponseEntity<Map<Long, Integer>> getVagasEInscritosPorCursoParaTurma(@PathVariable Long id) throws InstantiationException, ProtocolException, IOException, IOException {
        Map<Long, Integer> resultado = service.getListaAlunosPorCursoEmTurma(id);
        return ResponseEntity.ok(resultado);
    }
        
}
