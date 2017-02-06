/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.unmanaged.RequestTurma;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TurmaControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
    }

    @Test
    public void getTurmasRetornaOK() {
        ResponseEntity<List> response = restTemplate.getForEntity("/turmas", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void postTurmaOK() {
        RequestTurma rt = Fixture.from(RequestTurma.class).gimme("valido");
        rt.setCodigoDisciplina("TCC00173");
        ResponseEntity<Turma> response = restTemplate.postForEntity("/turmas", rt, Turma.class);
        assertEquals(rt.getCodigoTurma(), response.getBody().getCodigo());
        assertEquals(rt.getCodigoDisciplina(), response.getBody().getDisciplina().getCodigo());
        assertEquals(rt.getAnosemestre(), response.getBody().getAnosemestre());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void postTurmaSemCodigoDisciplinaDaErro() {
        RequestTurma rt = Fixture.from(RequestTurma.class).gimme("valido");
        ResponseEntity<Turma> response = restTemplate.postForEntity("/turmas", rt, Turma.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postTurmaSemCodigoTurmaDaErro() {
        RequestTurma rt = Fixture.from(RequestTurma.class).gimme("valido");
        rt.setCodigoDisciplina("TCC00173");
        rt.setCodigoTurma(null);
        ResponseEntity<Turma> response = restTemplate.postForEntity("/turmas", rt, Turma.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postTurmaSemAnosemestreDaErro() {
        RequestTurma rt = Fixture.from(RequestTurma.class).gimme("valido");
        rt.setCodigoDisciplina("TCC00173");
        rt.setAnosemestre(null);
        ResponseEntity<Turma> response = restTemplate.postForEntity("/turmas", rt, Turma.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
    
    @Test
    public void postTurmaNuloDaErro() {
        ResponseEntity<Turma> response = restTemplate.postForEntity("/turmas", null, Turma.class);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }
}
