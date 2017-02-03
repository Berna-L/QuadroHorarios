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
import br.uff.id.bernardolopes.quadrohorarios.util.RequestTurma;
import java.util.List;
import java.util.Map;
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

    private VagaTurmaCursoDAO vagaTurmaCursoDAO;

    private DisciplinaDAO disciplinaDAO;

    @Autowired
    private TurmaService service;

    private static final String CODIGO_TURMA = "T1";
    private static final String ANOSEMESTRE = "2017_1";

    private static final String CODIGO_DISCIPLINA_INEXISTENTE = "TES99999";

    @Before
    public void setUp() {
        turmaDAO = mock(TurmaDAO.class);
        vagaTurmaCursoDAO = mock(VagaTurmaCursoDAO.class);
        disciplinaDAO = mock(DisciplinaDAO.class);
        service.setTurmaDAO(turmaDAO);
        service.setVagaTurmaCursoDAO(vagaTurmaCursoDAO);
        service.setDisciplinaDAO(disciplinaDAO);
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
    }

    /* Criação de turma
    Testes para casos OK*/
    @Test
    public void insereNoBancoComObjetoDisciplina() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Hora do show
        Turma t = service.criarTurma(CODIGO_TURMA, ANOSEMESTRE, d);
        //Asserções de valor
        assertEquals(CODIGO_TURMA, t.getCodigo());
        assertEquals(d, t.getDisciplina());
        //Verificação de chamadas
        verify(turmaDAO).findByCodigoAndAnosemestreAndDisciplina(CODIGO_TURMA, ANOSEMESTRE, d);
        verify(turmaDAO).save(t);
    }

    @Test
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
        Turma t = service.criarTurma(CODIGO_TURMA, ANOSEMESTRE, d.getCodigo());
        //Asserções de valor
        assertEquals(CODIGO_TURMA, t.getCodigo());
        assertEquals(ANOSEMESTRE, t.getAnosemestre());
        assertEquals(d.getCodigo(), t.getDisciplina().getCodigo());
        //Verificações de chamada
        verify(disciplinaDAO).findByCodigo(d.getCodigo());
        verify(turmaDAO).findByCodigoAndAnosemestreAndDisciplina(CODIGO_TURMA, ANOSEMESTRE, d);
        verify(turmaDAO).save(t);
    }

    @Test
    public void insereNoBancoComRequest() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Criação dos mocks
        RequestTurma request = mock(RequestTurma.class);
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock request
        when(request.getCodigoDisciplina()).thenReturn(d.getCodigo());
        when(request.getCodigoTurma()).thenReturn(CODIGO_TURMA);
        when(request.getAnosemestre()).thenReturn(ANOSEMESTRE);
        when(request.isValid()).thenReturn(Boolean.TRUE);
        //Configuração do mock mockList
        when(mockList.get(0)).thenReturn(d);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(d.getCodigo())).thenReturn(mockList);
        //Hora do show
        Turma t = service.criarTurma(request);
        //Asserções de valor
        assertEquals(CODIGO_TURMA, t.getCodigo());
        assertEquals(ANOSEMESTRE, t.getAnosemestre());
        assertEquals(d.getCodigo(), t.getDisciplina().getCodigo());
        //Verificações de chamada
        verify(disciplinaDAO).findByCodigo(d.getCodigo());
        verify(request).isValid();
        verify(turmaDAO).findByCodigoAndAnosemestreAndDisciplina(CODIGO_TURMA, ANOSEMESTRE, d);
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
        when(turmaDAO.findByCodigoAndAnosemestreAndDisciplina(CODIGO_TURMA, ANOSEMESTRE, d)).thenReturn(mockList);
        //Execeção aqui
        service.criarTurma(CODIGO_TURMA, ANOSEMESTRE, d);
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
        service.criarTurma(null, ANOSEMESTRE, d.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComAnosemestreNuloDaErro() {
        //Criação por fixture
        Disciplina d = Fixture.from(Disciplina.class).gimme("valido");
        //Criação de mock
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock mockList
        when(mockList.get(0)).thenReturn(d);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(d.getCodigo())).thenReturn(mockList);
        //Exceção aqui
        service.criarTurma(CODIGO_TURMA, null, d.getCodigo());
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComDisciplinaNuloDaErro() {
        service.criarTurma(CODIGO_TURMA, ANOSEMESTRE, (Disciplina) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComDisciplinaInexistenteDaErro() {
        //Criação de mock
        List<Disciplina> mockList = mock(List.class);
        //Configuração do mock mockList
        when(mockList.get(0)).thenReturn(null);
        //Configuração do mock disciplinaDAO
        when(disciplinaDAO.findByCodigo(CODIGO_DISCIPLINA_INEXISTENTE)).thenReturn(mockList);
        //Exceção aqui
        service.criarTurma(CODIGO_TURMA, ANOSEMESTRE, CODIGO_DISCIPLINA_INEXISTENTE);
    }

    /* Obtenção de vagas
    Testes para casos OK*/
    @Test
    public void getVagasPorTurmaOK() {
        //Criação por fixture
        Turma t = Fixture.from(Turma.class).gimme("turma-disciplina-fixas");
        List<VagaTurmaCurso> listaEsperada = Fixture.from(VagaTurmaCurso.class).gimme(15, "turma-disciplina-fixas");
        //Configuração do mock
        when(vagaTurmaCursoDAO.findByTurma(t)).thenReturn(listaEsperada);
        //Hora do show
        Map<Curso, Integer> mapa = service.getVagasPorCurso(t);
        //Asserções de valor
        for (VagaTurmaCurso vtc : listaEsperada) {
            assertEquals(vtc.getVagas(), (long) mapa.get(vtc.getCurso()));
        }
        //Verificação de chamadas
        verify(vagaTurmaCursoDAO).findByTurma(t);
    }

    /* Criação de turma
    Testes para exceções*/
    @Test(expected = IllegalArgumentException.class)
    public void getVagasPorTurmaComNuloDaErro() {
        service.getVagasPorCurso(null);
    }
}
