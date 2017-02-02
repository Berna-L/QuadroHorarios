/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TurmaServiceTest {

    private TurmaDAO turmaDAO;

    private DisciplinaDAO disciplinaDAO;

    @Autowired
    private TurmaService service;

    private static final String CODIGO_TURMA = "T1";
    private static final String CODIGO_TURMA_2 = "T2";

    private static final String CODIGO_TURMA_EXISTENTE = "A1";
    private static final String CODIGO_DISCIPLINA_EXISTENTE = "TCC00173";

    private static final String CODIGO_DISCIPLINA = "GMA00108";
    private static final String CODIGO_DISCIPLINA_INEXISTENTE = "TES99999";

    @Before
    public void setUp() {
        turmaDAO = mock(TurmaDAO.class);
        disciplinaDAO = mock(DisciplinaDAO.class);
        service.setTurmaDAO(turmaDAO);
        service.setDisciplinaDAO(disciplinaDAO);
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
    }

    @Test
    public void insereNoBancoComObjetoDisciplina() {
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        Turma t = service.criarTurma(CODIGO_TURMA, d);
        assertEquals(CODIGO_TURMA, t.getCodigo());
        assertEquals(d, t.getDisciplina());
        verify(turmaDAO).findByCodigoAndDisciplina(CODIGO_TURMA, d);
        verify(turmaDAO).save(t);
    }

    @Test
    public void insereNoBancoComCodigoDisciplina() {
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        List<Disciplina> mockList = mock(List.class);
        when(mockList.get(0)).thenReturn(d);
        when(disciplinaDAO.findByCodigo(d.getCodigo())).thenReturn(mockList);
        Turma t = service.criarTurma(CODIGO_TURMA_2, d.getCodigo());
        assertEquals(CODIGO_TURMA_2, t.getCodigo());
        assertEquals(d.getCodigo(), t.getDisciplina().getCodigo());
    }

    @Test(expected = InstanceAlreadyExistsException.class)
    public void insereNoBancoJaExiste() {
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        List<Turma> mockList = mock(List.class);
        when(turmaDAO.findByCodigoAndDisciplina(CODIGO_TURMA, d)).thenReturn(mockList);
        when(mockList.isEmpty()).thenReturn(Boolean.FALSE);
        service.criarTurma(CODIGO_TURMA, d);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCodigoNuloDaErro() {
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        List<Disciplina> mockList = mock(List.class);
        when(mockList.get(0)).thenReturn(d);
        when(disciplinaDAO.findByCodigo(d.getCodigo())).thenReturn(mockList);
        service.criarTurma(null, d.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComDisciplinaNuloDaErro() {
        service.criarTurma(CODIGO_TURMA, (Disciplina) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComDisciplinaInexistenteDaErro() {
        List<Disciplina> mockList = mock(List.class);
        when(mockList.get(0)).thenReturn(null);
            when(disciplinaDAO.findByCodigo(CODIGO_DISCIPLINA_INEXISTENTE)).thenReturn(mockList);
        service.criarTurma(CODIGO_TURMA, CODIGO_DISCIPLINA_INEXISTENTE);
    }
}
