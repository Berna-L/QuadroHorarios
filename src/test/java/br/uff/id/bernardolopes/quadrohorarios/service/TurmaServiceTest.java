/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.VagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.VagaTurmaCursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.controller.model.RequestTurma;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author bernardolopes at id.uff.br
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TurmaServiceTest {

    private TurmaDAO turmaDAO;

    private DisciplinaDAO disciplinaDAO;

    private TurmaService service;

    private static final Long ID_TURMA = 1L;
    private static final String CODIGO_TURMA = "T1";
    private static final String ANO_SEMESTRE = "2017_1";

    private static final String CODIGO_DISCIPLINA_INEXISTENTE = "TES99999";

    @Before
    public void setUp() {
        service = new TurmaService();
        turmaDAO = mock(TurmaDAO.class);
        disciplinaDAO = mock(DisciplinaDAO.class);
        service.setTurmaDAO(turmaDAO);
        service.setDisciplinaDAO(disciplinaDAO);
    }

    @BeforeClass
    public static void beforeClass() {
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
    }

    /*Obtenção de turmas
    Testes para casos OK*/
    @Test
    public void getTurmasOK() {
        //Criação por fixture
        List<Turma> turmasEsperadas = Fixture.from(Turma.class).gimme(5, "valido");
        //Configuração do mock
        when(turmaDAO.findAll()).thenReturn(turmasEsperadas);
        //Hora do show
        List<Turma> turmas = service.getTurmas();
        //Asserções de valor
        assertEquals(turmasEsperadas, turmas);
        //Verificação de chamadas
        verify(turmaDAO).findAll();
    }

    /*Obtenção de uma turma
    Testes para casos OK*/
    @Test
    public void getTurmaOK() {
        //Criação do mock
        Turma turmaEsperada = mock(Turma.class);
        //Configuração do mock turmaDAO
        when(turmaDAO.findOne(ID_TURMA)).thenReturn(turmaEsperada);
        //Hora do show
        Turma turma = service.getTurma(ID_TURMA);
        //Asserções de valor
        assertEquals(turmaEsperada, turma);
        //Verificação de chamadas
        verify(turmaDAO).findOne(ID_TURMA);
    }

    /* Criação de turma
    Testes para casos OK*/
    @Test
    @Transactional
    @Rollback
    public void insereNoBancoComObjetoDisciplina() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Hora do show
        Turma t = service.criarTurma(Optional.of(CODIGO_TURMA), Optional.of(ANO_SEMESTRE), Optional.of(d));
        //Asserções de valor
        assertEquals(CODIGO_TURMA, t.getCodigo());
        assertEquals(d, t.getDisciplina());
        //Verificação de chamadas
        verify(turmaDAO).findByCodigoAndAnoSemestreAndDisciplina(CODIGO_TURMA, ANO_SEMESTRE, d);
        verify(turmaDAO).save(t);
    }

    @Test
    @Transactional
    @Rollback
    public void insereNoBancoComCodigoDisciplina() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Criação de mock
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock mockList
        when(mockList.get(0)).thenReturn(d);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(d.getCodigo())).thenReturn(mockList);
        //Hora do show
        Turma t = service.criarTurma(Optional.of(CODIGO_TURMA), Optional.of(ANO_SEMESTRE), d.getCodigo());
        //Asserções de valor
        assertEquals(CODIGO_TURMA, t.getCodigo());
        assertEquals(ANO_SEMESTRE, t.getAnoSemestre());
        assertEquals(d.getCodigo(), t.getDisciplina().getCodigo());
        //Verificações de chamada
        verify(disciplinaDAO).findByCodigo(d.getCodigo());
        verify(turmaDAO).findByCodigoAndAnoSemestreAndDisciplina(CODIGO_TURMA, ANO_SEMESTRE, d);
        verify(turmaDAO).save(t);
    }

    @Test
    @Transactional
    @Rollback
    public void insereNoBancoComRequest() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Criação dos mocks
        RequestTurma request = mock(RequestTurma.class);
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock request
        when(request.getCodigoDisciplina()).thenReturn(d.getCodigo());
        when(request.getCodigoTurma()).thenReturn(CODIGO_TURMA);
        when(request.getAnoSemestre()).thenReturn(ANO_SEMESTRE);
        when(request.isValid()).thenReturn(Boolean.TRUE);
        //Configuração do mock mockList
        when(mockList.get(0)).thenReturn(d);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(d.getCodigo())).thenReturn(mockList);
        //Hora do show
        Turma t = service.criarTurma(request);
        //Asserções de valor
        assertEquals(CODIGO_TURMA, t.getCodigo());
        assertEquals(ANO_SEMESTRE, t.getAnoSemestre());
        assertEquals(d.getCodigo(), t.getDisciplina().getCodigo());
        //Verificações de chamada
        verify(disciplinaDAO).findByCodigo(d.getCodigo());
        verify(request).isValid();
        verify(turmaDAO).findByCodigoAndAnoSemestreAndDisciplina(CODIGO_TURMA, ANO_SEMESTRE, d);
        verify(turmaDAO).save(t);
    }

    /* Criação de turma
    Testes para exceções*/
    @Test(expected = InstanceAlreadyExistsException.class)
    public void insereNoBancoJaExiste() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Criação de mock
        List<Turma> mockList = mock(List.class);
        //Configuração do mock mockList
        when(mockList.isEmpty()).thenReturn(Boolean.FALSE);
        //Configuração do mock turmaDAO
        when(turmaDAO.findByCodigoAndAnoSemestreAndDisciplina(CODIGO_TURMA, ANO_SEMESTRE, d)).thenReturn(mockList);
        //Execeção aqui
        service.criarTurma(Optional.of(CODIGO_TURMA), Optional.of(ANO_SEMESTRE), Optional.of(d));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCodigoNuloDaErro() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Criação de mock
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock mockList
        when(mockList.get(0)).thenReturn(d);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(d.getCodigo())).thenReturn(mockList);
        //Exceção aqui
        service.criarTurma(Optional.empty(), Optional.of(ANO_SEMESTRE), d.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComAnoSemestreNuloDaErro() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Criação de mock
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock mockList
        when(mockList.get(0)).thenReturn(d);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(d.getCodigo())).thenReturn(mockList);
        //Exceção aqui
        service.criarTurma(Optional.of(CODIGO_TURMA), Optional.empty(), d.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComDisciplinaNuloDaErro() {
        service.criarTurma(Optional.of(CODIGO_TURMA), Optional.of(ANO_SEMESTRE), Optional.empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComDisciplinaInexistenteDaErro() {
        //Criação de mock
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock mockList
        when(mockList.get(0)).thenThrow(IndexOutOfBoundsException.class);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(CODIGO_DISCIPLINA_INEXISTENTE)).thenReturn(mockList);
        //Exceção aqui
        service.criarTurma(Optional.of(CODIGO_TURMA), Optional.of(ANO_SEMESTRE), CODIGO_DISCIPLINA_INEXISTENTE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComRequestInvalidoDaErro() {
        //Criação do mock
        RequestTurma request = mock(RequestTurma.class);
        //Configuração do mock
        when(request.isValid()).thenReturn(Boolean.FALSE);
        //Exceção aqui
        service.criarTurma(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComRequestNuloDaErro() {
        //Criação do mock
        RequestTurma request = null;
        //Exceção aqui
        service.criarTurma(request);
    }

}
