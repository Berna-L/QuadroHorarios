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
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.controller.model.RequestDisciplina;
import java.util.List;
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
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author bernardolopes at id.uff.br
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DisciplinaServiceTest {

    private DisciplinaDAO disciplinaDAO;

    private CursoDAO cursoDAO;

    private DisciplinaService service;

    private static final Long ID_DISCIPLINA = 1L;
    private static final String CODIGO_DISCIPLINA = "TES40404";
    private static final String NOME_DISCIPLINA = "Testes de Configuração";

    private static final String CODIGO_DISCIPLINA_2 = "TES42424";
    private static final String NOME_DISCIPLINA_2 = "Testes de Universo";

    private static final Long CODIGO_CURSO_INEXISTENTE = 0L;
    private static final Long CODIGO_CURSO_QUALQUER = 10L;

    @Before
    public void setUp() {
        service = new DisciplinaService();
        disciplinaDAO = mock(DisciplinaDAO.class);
        cursoDAO = mock(CursoDAO.class);
        service.setDisciplinaDAO(disciplinaDAO);
        service.setCursoDAO(cursoDAO);
    }

    @BeforeClass
    public static void beforeClass() {
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
    }

    /*Obtenção de disciplinas
    Testes para casos OK*/
    @Test
    public void getDisciplinasOK() {
        //Criação por fixture
        List<Disciplina> disciplinasEsperadas = Fixture.from(Disciplina.class).gimme(5, "valido");
        //Configuração do mock
        when(disciplinaDAO.findAll()).thenReturn(disciplinasEsperadas);
        //Hora do show
        List<Disciplina> turmas = service.getDisciplinas();
        //Asserções de valor
        assertEquals(disciplinasEsperadas, turmas);
        //Verificação de chamadas
        verify(disciplinaDAO).findAll();
    }

    /*Obtenção de uma disciplina
    Testes para casos OK*/
    @Test
    public void getDisciplinaOK() {
        //Criação do mock
        Disciplina turmaEsperada = mock(Disciplina.class);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findOne(ID_DISCIPLINA)).thenReturn(turmaEsperada);
        //Hora do show
        Disciplina turma = service.getDisciplina(ID_DISCIPLINA);
        //Asserções de valor
        assertEquals(turmaEsperada, turma);
        //Verificação de chamadas
        verify(disciplinaDAO).findOne(ID_DISCIPLINA);
    }

    //Testes para casos OK
    @Test
    @Transactional
    @Rollback
    public void insereNoBancoComObjetoCurso() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("valido");
        //Hora do show
        Disciplina d = service.criarDisciplina(Optional.of(CODIGO_DISCIPLINA), Optional.of(NOME_DISCIPLINA), Optional.of(c));
        //Asserções de valor
        assertEquals(CODIGO_DISCIPLINA, d.getCodigo());
        assertEquals(NOME_DISCIPLINA, d.getNome());
        assertEquals(c, d.getCurso());
        //Verificação de chamadas
        verify(disciplinaDAO).findByCodigo(CODIGO_DISCIPLINA);
        verify(disciplinaDAO).save(d);
    }

    @Test
    @Transactional
    @Rollback
    public void insereNoBancoComCodigoCurso() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("valido");
        //Configuração do mock
        when(cursoDAO.findOne(c.getCodigo())).thenReturn(c);
        //Hora do show
        Disciplina d = service.criarDisciplina(Optional.of(CODIGO_DISCIPLINA), Optional.of(NOME_DISCIPLINA), c.getCodigo());
        //Asserções de valor
        assertEquals(CODIGO_DISCIPLINA, d.getCodigo());
        assertEquals(NOME_DISCIPLINA, d.getNome());
        assertEquals(c.getCodigo(), d.getCurso().getCodigo());
        //Verificação de chamadas
        verify(cursoDAO).findOne(c.getCodigo());
        verify(disciplinaDAO).findByCodigo(CODIGO_DISCIPLINA);
        verify(disciplinaDAO).save(d);
    }

    @Test
    @Transactional
    @Rollback
    public void insereNoBancoComRequest() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("valido");
        //Criação do mock
        RequestDisciplina request = mock(RequestDisciplina.class);
        //Configuração do mock request
        when(request.getCodigoCurso()).thenReturn(c.getCodigo());
        when(request.getCodigoDisciplina()).thenReturn(CODIGO_DISCIPLINA);
        when(request.getNome()).thenReturn(NOME_DISCIPLINA);
        when(request.isValid()).thenReturn(Boolean.TRUE);
        //Configuração do mock cursoDAO
        when(cursoDAO.findOne(c.getCodigo())).thenReturn(c);
        //Hora do show
        Disciplina d = service.criarDisciplina(request);
        //Asserções de valor
        assertEquals(CODIGO_DISCIPLINA, d.getCodigo());
        assertEquals(NOME_DISCIPLINA, d.getNome());
        assertEquals(c.getCodigo(), d.getCurso().getCodigo());
        //Verificação de chamadas
        verify(cursoDAO).findOne(c.getCodigo());
        verify(request).isValid();
        verify(disciplinaDAO).findByCodigo(CODIGO_DISCIPLINA);
        verify(disciplinaDAO).save(d);
    }

    //Testes de exceção
    @Test(expected = InstanceAlreadyExistsException.class)
    public void insereNoBancoJaExiste() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("valido");
        //Criação de mock
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(CODIGO_DISCIPLINA)).thenReturn(mockList);
        //Configuração do mock mockList
        when(mockList.isEmpty()).thenReturn(Boolean.FALSE);
        //Exceção aqui
        service.criarDisciplina(Optional.of(CODIGO_DISCIPLINA), Optional.of(NOME_DISCIPLINA), Optional.of(c));
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCodigoNuloDaErro() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("valido");
        //Configuração do mock
        when(cursoDAO.findOne(c.getCodigo())).thenReturn(c);
        //Exceção aqui
        service.criarDisciplina(Optional.empty(), Optional.of(NOME_DISCIPLINA), c.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComNomeNuloDaErro() {
        //Criação por fixture
        Curso c = Fixture.from(Curso.class).gimme("valido");
        //Configuração do mock
        when(cursoDAO.findOne(c.getCodigo())).thenReturn(c);
        //Exceção aqui
        service.criarDisciplina(Optional.of(CODIGO_DISCIPLINA), Optional.empty(), c.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCursoNuloDaErro() {
        service.criarDisciplina(Optional.of(CODIGO_DISCIPLINA), Optional.of(NOME_DISCIPLINA), Optional.empty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCursoInexistenteDaErro() {
        //Configuração do mock
        when(cursoDAO.findOne(CODIGO_CURSO_INEXISTENTE)).thenReturn(null);
        //Exceção aqui
        service.criarDisciplina(Optional.of(CODIGO_DISCIPLINA), Optional.of(NOME_DISCIPLINA), CODIGO_CURSO_INEXISTENTE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComRequestInvalidoDaErro() {
        //Criação do mock
        RequestDisciplina request = mock(RequestDisciplina.class);
        //Configuração do mock
        when(request.isValid()).thenReturn(Boolean.FALSE);
        //Exceção aqui
        service.criarDisciplina(request);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComRequestNuloDaErro() {
        //Criação do mock
        RequestDisciplina request = null;
        //Exceção aqui
        service.criarDisciplina(request);
    }

}
