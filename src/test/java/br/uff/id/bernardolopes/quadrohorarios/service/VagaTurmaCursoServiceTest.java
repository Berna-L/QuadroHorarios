/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.VagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.controller.model.RequestVagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.VagaTurmaCursoDAO;
import java.io.IOException;
import java.net.ProtocolException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author bernardolopes at id.uff.br
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VagaTurmaCursoServiceTest {

    private VagaTurmaCursoDAO vagaTurmaCursoDAO;
    private CursoDAO cursoDAO;
    private TurmaDAO turmaDAO;
    private RestTemplate rest;

//    @Autowired
    private VagaTurmaCursoService service;

    private final String ANO_SEMESTRE = "2017_1";
    private final String ANO_SEMESTRE_INEXISTENTE = "2012_2";

    private final Long CODIGO_CURSO_INEXISTENTE = 0L;

    @Before
    public void setUp() {
        service = new VagaTurmaCursoService();
        vagaTurmaCursoDAO = mock(VagaTurmaCursoDAO.class);
        cursoDAO = mock(CursoDAO.class);
        turmaDAO = mock(TurmaDAO.class);
        rest = mock(RestTemplate.class);
        service.setVagaTurmaCursoDAO(vagaTurmaCursoDAO);
        service.setCursoDAO(cursoDAO);
        service.setTurmaDAO(turmaDAO);
        service.setRest(rest);
    }

    @BeforeClass
    public static void beforeClass() {
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
    }

    /* Obtenção de turmas por curso e AnoSemestre
    Testes para casos OK */
    @Test
    public void getTurmasPorCursoEAnoSemestreOK() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("curso-fixo");
        List<VagaTurmaCurso> listaVTC = Fixture.from(VagaTurmaCurso.class).gimme(10, "curso-fixo");
        //Configuração do mock
        when(vagaTurmaCursoDAO.findByCurso(c)).thenReturn(listaVTC);
        //Hora do show
        List<Turma> turmas = service.getTurmasParaCursoEAnoSemestre(c, ANO_SEMESTRE);
        //Asserções de valor
        assertEquals(listaVTC.size(), turmas.size()); //Como curso e AS são fixos no fixture...
        for (VagaTurmaCurso vtc : listaVTC) {
            assertTrue(turmas.contains(vtc.getTurma()));
            turmas.remove(vtc.getTurma()); //Verificar se não há duplicatas
        }
    }

    @Test
    public void getTurmasPorCodigoCursoEAnoSemestreOK() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("curso-fixo");
        List<VagaTurmaCurso> listaVTC = Fixture.from(VagaTurmaCurso.class).gimme(10, "curso-fixo");
        //Configuração do mock
        when(cursoDAO.findOne(c.getCodigo())).thenReturn(c);
        when(vagaTurmaCursoDAO.findByCurso(c)).thenReturn(listaVTC);
        //Hora do show
        List<Turma> turmas = service.getTurmasParaCursoEAnoSemestre(c.getCodigo(), ANO_SEMESTRE);
        //Asserções de valor
        assertEquals(listaVTC.size(), turmas.size()); //Como curso e AS são fixos no fixture...
        for (VagaTurmaCurso vtc : listaVTC) {
            assertTrue(turmas.contains(vtc.getTurma()));
            turmas.remove(vtc.getTurma()); //Verificar se não há duplicatas
        }
    }

    /* Obtenção de turmas por curso e AnoSemestre
    Testes para exceções */
    @Test(expected = IllegalArgumentException.class)
    public void getTurmasPorCursoComAnoSemestreInexistenteDaErro() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("curso-fixo");
        List<VagaTurmaCurso> listaVTC = Fixture.from(VagaTurmaCurso.class).gimme(10, "curso-fixo");
        //Configuração do mock
        when(vagaTurmaCursoDAO.findByCurso(c)).thenReturn(listaVTC);
        //Exceção aqui
        List<Turma> turmas = service.getTurmasParaCursoEAnoSemestre(c, ANO_SEMESTRE_INEXISTENTE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTurmasPorCursoNuloDaErro() {
        List<VagaTurmaCurso> listaVTC = new ArrayList<>();
        //Configuração do mock
        when(vagaTurmaCursoDAO.findByCurso(Matchers.any())).thenReturn(listaVTC);
        //Exceção aqui
        List<Turma> turmas = service.getTurmasParaCursoEAnoSemestre((Curso) null, ANO_SEMESTRE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTurmasPorCursoSemTurmasDaErro() {
        Curso c = Fixture.from(Curso.class).gimme("curso-fixo");
        List<VagaTurmaCurso> listaVTC = new ArrayList<>();
        //Configuração do mock
        when(vagaTurmaCursoDAO.findByCurso(c)).thenReturn(listaVTC);
        //Exceção aqui
        List<Turma> turmas = service.getTurmasParaCursoEAnoSemestre(c, ANO_SEMESTRE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getTurmasPorCodigoCursoInexistenteDaErro() {
        //Criação por fixture
//        Curso c = Fixture.from(Curso.class).gimme("curso-fixo");;
        List<VagaTurmaCurso> listaVTC = Fixture.from(VagaTurmaCurso.class).gimme(10, "curso-fixo");
        //Configuração do mock
        when(cursoDAO.findOne(CODIGO_CURSO_INEXISTENTE)).thenReturn(null);
        //Hora do show
        List<Turma> turmas = service.getTurmasParaCursoEAnoSemestre(CODIGO_CURSO_INEXISTENTE, ANO_SEMESTRE);

    }


    /* Obtenção de vagas
    Testes para casos OK */
//    @Test
//    public void getVagasPorTurmaOK() {
//        //Criação por fixture
//        Turma t = Fixture.from(Turma.class).gimme("turma-disciplina-fixas");
//        List<VagaTurmaCurso> listaEsperada = Fixture.from(VagaTurmaCurso.class).gimme(15, "turma-disciplina-fixas");
//        //Configuração do mock
//        when(vagaTurmaCursoDAO.findByTurma(t)).thenReturn(listaEsperada);
//        //Hora do show
//        Map<Curso, Integer> mapa = service.getVagasPorCurso(t);
//        //Asserções de valor
//        for (VagaTurmaCurso vtc : listaEsperada) {
//            assertEquals(vtc.getVagas(), mapa.get(vtc.getCurso()));
//        }
//        //Verificação de chamadas
//        verify(vagaTurmaCursoDAO).findByTurma(t);;
//    }
//
//    /* Criação de turma
//    Testes para exceções*/
//    @Test(expected = IllegalArgumentException.class)
//    public void getVagasPorTurmaComNuloDaErro() {
//        service.getVagasPorCurso(null);
//    }
//    
    /*
    Obtenção de vagas
    Testes para casos OK
     */
    @Test
    public void getListaVagasEmTurmaPorCursoComObjetoOK() {
        //Criação por fixture
        Turma t = Fixture.from(Turma.class).gimme("turma-disciplina-fixas");
        List<VagaTurmaCurso> listaEsperada = Fixture.from(VagaTurmaCurso.class).gimme(3, "turma-disciplina-fixas");
        //Configuração do mock
        when(vagaTurmaCursoDAO.findByTurma(t)).thenReturn(listaEsperada);
        //Hora do show
        Map<Long, Integer> mapa = service.getListaVagasEmTurmaPorCurso(t);
        //Asserções de valor
        for (VagaTurmaCurso vtc : listaEsperada) {
            Long codigoCurso = vtc.getCurso().getCodigo();
            assertTrue(mapa.containsKey(codigoCurso));
            assertEquals(vtc.getVagas(), mapa.get(codigoCurso));
        }
    }

    @Test
    public void getListaVagasEmTurmaPorCursoComIdOK() {
        //Criação por fixture
        Turma t = Fixture.from(Turma.class).gimme("turma-disciplina-fixas");
        List<VagaTurmaCurso> listaEsperada = Fixture.from(VagaTurmaCurso.class).gimme(3, "turma-disciplina-fixas");
        //Configuração do mock
        when(turmaDAO.findOne(t.getId())).thenReturn(t);
        when(vagaTurmaCursoDAO.findByTurma(t)).thenReturn(listaEsperada);
        //Hora do show
        Map<Long, Integer> mapa = service.getListaVagasEmTurmaPorCurso(t.getId());
        //Asserções de valor
        for (VagaTurmaCurso vtc : listaEsperada) {
            Long codigoCurso = vtc.getCurso().getCodigo();
            assertTrue(mapa.containsKey(codigoCurso));
            assertEquals(vtc.getVagas(), mapa.get(codigoCurso));
        }
    }

    /*
    Obtenção de vagas
    Testes para exceções
     */
    @Test(expected = IllegalArgumentException.class)
    public void getListaVagasEmTurmaPorCursoComIdInexistenteDaErro() {
        //Configuração do mock
        when(turmaDAO.findOne(Long.MAX_VALUE)).thenReturn(null);
//        when(vagaTurmaCursoDAO.findByTurma(t)).thenReturn(listaEsperada);
        //Exceção aqui
        service.getListaVagasEmTurmaPorCurso(Long.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListaVagasEmTurmaPorCursoComObjetoNuloDaErro() {
        //Exceção aqui
        service.getListaVagasEmTurmaPorCurso((Turma) null);
    }

    /*
    Obtenção de inscritos
    Testes para casos OK
     */
    @Test
    public void getListaInscritosEmTurmaPorCursoComObjetoOK() throws IOException, InstantiationException {
        //Criação por fixture
        Turma t = Fixture.from(Turma.class).gimme("turma-disciplina-fixas");
        //Configuração do mock rest
        Map<String, Integer> mapaEsperado = new HashMap<>();
        mapaEsperado.put("31", 5);
        mapaEsperado.put("32", 7);
        mapaEsperado.put("33", 10);
        when(rest.getForObject("http://test" + t.getId(), Map.class)).thenReturn(mapaEsperado);
        //Hora do show
        Map<String, Integer> mapa = service.getListaInscritosEmTurmaPorCurso(t, "http://test");
        //Asserções de valor
        for (String k : mapaEsperado.keySet()) {
            assertTrue(mapa.containsKey(k));
            assertEquals(mapaEsperado.get(k), mapa.get(k));
        }
    }

    @Test
    public void getListaInscritosEmTurmaPorCursoComIdOK() throws IOException, InstantiationException {
        //Criação por fixture
        Turma t = Fixture.from(Turma.class).gimme("turma-disciplina-fixas");
        //Configuração do mock
        when(turmaDAO.findOne(t.getId())).thenReturn(t);
        //Configuração do mock rest
        Map<String, Integer> mapaEsperado = new HashMap<>();
        mapaEsperado.put("31", 5);
        mapaEsperado.put("32", 7);
        mapaEsperado.put("33", 10);
        when(rest.getForObject("http://test" + t.getId(), Map.class)).thenReturn(mapaEsperado);
        //Hora do show
        Map<String, Integer> mapa = service.getListaInscritosEmTurmaPorCurso(t.getId(), "http://test");
        //Asserções de valor
        for (String k : mapaEsperado.keySet()) {
            assertTrue(mapa.containsKey(k));
            assertEquals(mapaEsperado.get(k), mapa.get(k));
        }
    }

    /*
    Obtenção de inscritos
    Testes para exceções
     */
    @Test(expected = IllegalArgumentException.class)
    public void getListaInscritosEmTurmaPorCursoComIdInexistenteDaErro() throws ProtocolException, IOException, InstantiationException {
        //Configuração do mock
        when(turmaDAO.findOne(Long.MAX_VALUE)).thenReturn(null);
//        when(vagaTurmaCursoDAO.findByTurma(t)).thenReturn(listaEsperada);
        //Exceção aqui
        service.getListaInscritosEmTurmaPorCurso(Long.MAX_VALUE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getListaInscritosEmTurmaPorCursoComObjetoNuloDaErro() {
        //Exceção aqui
        service.getListaVagasEmTurmaPorCurso((Turma) null);
    }
}
