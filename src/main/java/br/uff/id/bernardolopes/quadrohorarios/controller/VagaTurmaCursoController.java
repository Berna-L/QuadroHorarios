/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.uff.id.bernardolopes.quadrohorarios.controller.model.ResponseVagasInscritos;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.service.VagaTurmaCursoService;
import java.io.IOException;
import java.net.MalformedURLException;
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

    @GetMapping(value = "/turmas/{id}/inscritos")
    public ResponseEntity<Map<String, Integer>> getInscritosPorCursoParaTurma(@PathVariable Long id) throws InstantiationException, ProtocolException, IOException, IOException {
        Map<String, Integer> resultado = service.getListaInscritosEmTurmaPorCurso(id);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping(value = "/turmas/{id}/vagasInscritos")
    public ResponseEntity<List<ResponseVagasInscritos>> getVagasEInscritosPorCursoParaTurma(@PathVariable Long id) throws ProtocolException, IOException, InstantiationException, InstantiationException, MalformedURLException, InstantiationException {
        Map<String, Integer> inscritos = service.getListaInscritosEmTurmaPorCurso(id);
        Map<Long, Integer> vagas = service.getListaVagasEmTurmaPorCurso(id);
        return ResponseEntity.ok(construtorResponseVagasInscritos(vagas, inscritos));
    }
    
    private List<ResponseVagasInscritos> construtorResponseVagasInscritos(Map<Long, Integer> vagas, Map<String, Integer> inscritos){
        List<ResponseVagasInscritos> response = new ArrayList<>();
        for (Long k : vagas.keySet()){ //Teoricamente não teremos um curso com inscritos, mas sem vagas, logo keySet de vagas é superset do keySet de inscritos
            int inscritosAtual = inscritos.containsKey(k.toString()) ? inscritos.get(k.toString()) : 0;
            ResponseVagasInscritos rvi = new ResponseVagasInscritos(k, vagas.get(k), inscritosAtual);
            response.add(rvi);
        }
        return response;
    }

}
