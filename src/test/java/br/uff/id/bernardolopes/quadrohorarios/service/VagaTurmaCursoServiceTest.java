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
import br.uff.id.bernardolopes.quadrohorarios.model.unmanaged.RequestVagaTurmaCurso;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.VagaTurmaCursoDAO;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class VagaTurmaCursoServiceTest {

    private VagaTurmaCursoDAO vagaTurmaCursoDAO;

    @Autowired
    private VagaTurmaCursoService service;

    @Before
    public void setUp() {
        vagaTurmaCursoDAO = mock(VagaTurmaCursoDAO.class);
        service.setVagaTurmaCursoDAO(vagaTurmaCursoDAO);
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
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
