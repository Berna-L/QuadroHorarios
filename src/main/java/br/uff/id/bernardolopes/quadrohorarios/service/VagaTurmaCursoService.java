/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.VagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.controller.model.RequestVagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.VagaTurmaCursoDAO;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Service
public class VagaTurmaCursoService {

    @Autowired
    private VagaTurmaCursoDAO vagaTurmaCursoDAO;

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private CursoDAO cursoDAO;

    @Autowired
    private DisciplinaDAO disciplinaDAO;

    @Autowired
    private RestTemplate rest;

    private static final String REST_URL = "http://secure-meadow-72222.herokuapp.com/api/inscricoes/?turma_id=";

    public VagaTurmaCursoService() {
    }

    public void setVagaTurmaCursoDAO(VagaTurmaCursoDAO vagaTurmaCursoDAO) {
        this.vagaTurmaCursoDAO = vagaTurmaCursoDAO;
    }

    public void setCursoDAO(CursoDAO cursoDAO) {
        this.cursoDAO = cursoDAO;
    }

    public void setTurmaDAO(TurmaDAO turmaDAO) {
        this.turmaDAO = turmaDAO;
    }

    public void setRest(RestTemplate rest) {
        this.rest = rest;
    }

//    public VagaTurmaCurso criarVagaTurmaCurso(RequestVagaTurmaCurso request) {
//        if (request.isValid()) {
//            return criarVagaTurmaCurso(request.getIdTurma(), request.getIdCurso(), request.getVagas());
//        } else {
//            throw new IllegalArgumentException("Requisição inválida!");
//        }
//    }
//
//    public VagaTurmaCurso criarVagaTurmaCurso(Long idTurma, Long idCurso, Integer vagas) {
//        Optional<Turma> turma = Optional.ofNullable(turmaDAO.getOne(idTurma));
//        if (!turma.isPresent()) {
//            throw new IllegalArgumentException("Turma não encontrada!");
//        }
//        Optional<Curso> curso = Optional.ofNullable(cursoDAO.getOne(idCurso));
//        if (!curso.isPresent()) {
//            throw new IllegalArgumentException("Curso não encontrado!");
//        }
//        return criarVagaTurmaCurso(turma, curso, vagas);
//    }
//
//    public VagaTurmaCurso criarVagaTurmaCurso(Optional<Turma> turma, Optional<Curso> curso, Integer vagas) {
//        Turma turmaNaoOpt = turma.orElseThrow(() -> new IllegalArgumentException("Turma não pode ser nulo!"));
//        Curso cursoNaoOpt = curso.orElseThrow(() -> new IllegalArgumentException("Curso não pode ser nulo!"));
//        if (vagas < 1) {
//            throw new IllegalArgumentException("Vagas precisa ser positivo!");
//        }
//        if (vagaTurmaCursoDAO.findByTurmaAndCurso(turmaNaoOpt, cursoNaoOpt).isEmpty()) {
//            VagaTurmaCurso vtc = new VagaTurmaCurso(turmaNaoOpt, cursoNaoOpt, vagas);
//            vagaTurmaCursoDAO.save(vtc);
//            return vtc;
//        } else {
//            throw new InstanceAlreadyExistsException();
//        }
//    }

//    public Map<Curso, Integer> getVagasPorCurso(Turma turma) {
//        if (turma == null) {
//            throw new IllegalArgumentException("Turma não pode ser nulo!");
//        }
//        List<VagaTurmaCurso> lista = vagaTurmaCursoDAO.findByTurma(turma);
//        Map<Curso, Integer> relacaoVagas = new HashMap<>();
//        for (VagaTurmaCurso entrada : lista) {
//            relacaoVagas.put(entrada.getCurso(), entrada.getVagas());
//        }
//        return relacaoVagas;
//    }
    public Map<Long, Long> getQuantidadeTurmasPorCurso() {
        List<Object[]> lista = vagaTurmaCursoDAO.getCountByCurso();
        Map<Long, Long> mapa = new HashMap<>();
        for (Object[] array : lista) {
            Long cod = ((Curso) array[0]).getCodigo();
            mapa.put(cod, (Long) array[1]);
        }
        return mapa;
    }

    public Map<String, Long> getQuantidadeTurmasPorAnoSemestre() {
        List<Object[]> lista = vagaTurmaCursoDAO.getCountByAnoSemestre();
        Map<String, Long> mapa = new HashMap<>();
        for (Object[] array : lista) {
            mapa.put((String) array[0], (Long) array[1]);
        }
        return mapa;
    }

    public Map<String, Long> getQuantidadeVagasPorAnoSemestreParaDisciplina(Long id) {
        Disciplina d = disciplinaDAO.findOne(id);
        if (d == null) {
            throw new IllegalArgumentException("Disciplina não encontrada!");
        }
        List<Object[]> lista = vagaTurmaCursoDAO.getVagasByAnoSemestreForDisciplina(d);
        Map<String, Long> mapa = new HashMap<>();
        for (Object[] array : lista) {
            mapa.put((String) array[0], (Long) array[1]);
        }
        return mapa;
    }

    public List<Turma> getTurmasParaCursoEAnoSemestre(Long codigoCurso, String anoSemestre) {
        Curso curso = cursoDAO.findOne(codigoCurso);
        if (curso == null) {
            throw new IllegalArgumentException("Curso não encontrado com ID informado!");
        }
        return getTurmasParaCursoEAnoSemestre(curso, anoSemestre);
    }

    public List<Turma> getTurmasParaCursoEAnoSemestre(Curso curso, String anoSemestre) {
        anoSemestre = anoSemestreInformadoOuAtual(anoSemestre);
        List<VagaTurmaCurso> listaVTC = vagaTurmaCursoDAO.findByCurso(curso);
        if (listaVTC.size() == 0) {
            throw new IllegalArgumentException("Nenhuma turma disponível para o curso informado!");
        }
        List<Turma> turmas = listaTurmasDeUmAnoSemestre(listaVTC, anoSemestre);
        if (turmas.isEmpty()) {
            throw new IllegalArgumentException("Nenhuma turma disponível para o ano-semestre informado!");
        }
        return turmas;
    }

    private String anoSemestreInformadoOuAtual(String anoSemestre) {
        if (anoSemestre == null || anoSemestre.isEmpty()) {
            Calendar cal = Calendar.getInstance();
            anoSemestre = cal.get(Calendar.YEAR) + "_" + ((cal.get(Calendar.MONTH) / 6) + 1);
        }
        return anoSemestre;
    }

    private List<Turma> listaTurmasDeUmAnoSemestre(List<VagaTurmaCurso> listaVTC, String anoSemestre) {
        List<Turma> turmas = new ArrayList<>();
        for (VagaTurmaCurso vtc : listaVTC) {
            if (vtc.getTurma().getAnoSemestre().equals(anoSemestre)) {
                turmas.add(vtc.getTurma());
            }
        }
        return turmas;
    }

    public Map<Long, Integer> getListaVagasEmTurmaPorCurso(Long id) {
        Turma turma = turmaDAO.findOne(id);
        if (turma == null) {
            throw new IllegalArgumentException("Turma não encontrada com ID informado!");
        }
        return getListaVagasEmTurmaPorCurso(turma);
    }

    public Map<Long, Integer> getListaVagasEmTurmaPorCurso(Turma turma) {
        if (turma == null) {
            throw new IllegalArgumentException("Turma inválida!");
        }
        List<VagaTurmaCurso> lista = vagaTurmaCursoDAO.findByTurma(turma);
        Map<Long, Integer> mapa = new HashMap<>();
        for (VagaTurmaCurso vtc : lista) {
            mapa.put(vtc.getCurso().getCodigo(), vtc.getVagas());
        }
        return mapa;
    }

    public Map<String, Integer> getListaInscritosEmTurmaPorCurso(Long id) throws MalformedURLException, ProtocolException, IOException, InstantiationException {
        Turma turma = turmaDAO.findOne(id);
        if (turma == null) {
            throw new IllegalArgumentException("Turma não encontrada com ID informado!");
        }
        return VagaTurmaCursoService.this.getListaInscritosEmTurmaPorCurso(turma);
    }

    public Map<String, Integer> getListaInscritosEmTurmaPorCurso(Turma turma) throws MalformedURLException, ProtocolException, IOException, InstantiationException {
        return getListaInscritosEmTurmaPorCurso(turma, REST_URL);
    }

    public Map<String, Integer> getListaInscritosEmTurmaPorCurso(Long id, String url) throws MalformedURLException, ProtocolException, IOException, InstantiationException {
        Turma turma = turmaDAO.findOne(id);
        if (turma == null) {
            throw new IllegalArgumentException("Turma não encontrada com ID informado!");
        }
        return VagaTurmaCursoService.this.getListaInscritosEmTurmaPorCurso(turma, url);
    }

    public Map<String, Integer> getListaInscritosEmTurmaPorCurso(Turma turma, String url) throws IOException, InstantiationException {
        if (turma == null) {
            throw new IllegalArgumentException("Turma inválida!");
        }
        Map<String, Integer> resultadoId = rest.getForObject(url + turma.getId(), Map.class);
        return resultadoId;
    }
}
