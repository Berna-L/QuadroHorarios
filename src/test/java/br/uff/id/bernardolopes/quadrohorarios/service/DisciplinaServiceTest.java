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
import java.util.List;
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
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class DisciplinaServiceTest {

    private DisciplinaDAO disciplinaDAO;

    private CursoDAO cursoDAO;

    @Autowired
    private DisciplinaService service;

    private static final String CODIGO_DISCIPLINA = "TES40404";
    private static final String NOME_DISCIPLINA = "Testes de Configuração";

    private static final String CODIGO_DISCIPLINA_2 = "TES42424";
    private static final String NOME_DISCIPLINA_2 = "Testes de Universo";
    ;
    private static final long CODIGO_CURSO_INEXISTENTE = 0L;

    @Before
    public void setUp() {
        disciplinaDAO = mock(DisciplinaDAO.class);
        cursoDAO = mock(CursoDAO.class);
        service.setDisciplinaDAO(disciplinaDAO);
        service.setCursoDAO(cursoDAO);
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
    }

    @Test
    public void insereNoBancoComObjetoCurso() {
        Curso c = Fixture.from(Curso.class).gimme("valido");
        Disciplina d = service.criarDisciplina(CODIGO_DISCIPLINA, NOME_DISCIPLINA, c);
        assertEquals(CODIGO_DISCIPLINA, d.getCodigo());
        assertEquals(NOME_DISCIPLINA, d.getNome());
        assertEquals(c, d.getCurso());
        verify(disciplinaDAO).findByCodigo(CODIGO_DISCIPLINA);
        verify(disciplinaDAO).save(d);
    }

    @Test
    public void insereNoBancoComCodigoCurso() {
        Curso c = Fixture.from(Curso.class).gimme("valido");
        when(cursoDAO.findOne(c.getCodigo())).thenReturn(c);
        Disciplina d = service.criarDisciplina(CODIGO_DISCIPLINA, NOME_DISCIPLINA_2, c.getCodigo());
        assertEquals(CODIGO_DISCIPLINA, d.getCodigo());
        assertEquals(NOME_DISCIPLINA, d.getNome());
        assertEquals(c.getCodigo(), d.getCurso().getCodigo());
        verify(cursoDAO).findOne(c.getCodigo());
        verify(disciplinaDAO).findByCodigo(CODIGO_DISCIPLINA);
        verify(disciplinaDAO).save(d);
    }

    @Test(expected = InstanceAlreadyExistsException.class)
    public void insereNoBancoJaExiste() {
        Curso c = Fixture.from(Curso.class).gimme("valido");
        List<Disciplina> mockList = mock(List.class);
        when(disciplinaDAO.findByCodigo(CODIGO_DISCIPLINA)).thenReturn(mockList);
        when(mockList.isEmpty()).thenReturn(Boolean.FALSE);
        service.criarDisciplina(CODIGO_DISCIPLINA, NOME_DISCIPLINA, c);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCodigoNuloDaErro() {
        Curso c = Fixture.from(Curso.class).gimme("valido");
        when(cursoDAO.findOne(c.getCodigo())).thenReturn(c);
        service.criarDisciplina(null, NOME_DISCIPLINA, c.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComNomeNuloDaErro() {
        Curso c = Fixture.from(Curso.class).gimme("valido");
        when(cursoDAO.findOne(c.getCodigo())).thenReturn(c);
        service.criarDisciplina(CODIGO_DISCIPLINA, null, c.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCursoNuloDaErro() {
        service.criarDisciplina(null, NOME_DISCIPLINA, (Curso) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCursoInexistenteDaErro() {
        when(cursoDAO.findOne(CODIGO_CURSO_INEXISTENTE)).thenReturn(null);
        service.criarDisciplina(CODIGO_DISCIPLINA, NOME_DISCIPLINA, CODIGO_CURSO_INEXISTENTE);
    }
}
