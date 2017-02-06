/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.unmanaged.RequestDisciplina;
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
public class DisciplinaControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
    }

    @Test
    public void getDisciplinasRetornaOK() {
        ResponseEntity<List> response = restTemplate.getForEntity("/disciplinas", List.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void postDisciplinaOK() {
        RequestDisciplina rd = Fixture.from(RequestDisciplina.class).gimme("valido");
        rd.setCodigoCurso(31L);
        ResponseEntity<Disciplina> response = restTemplate.postForEntity("/disciplinas", rd, Disciplina.class);
        assertEquals(rd.getCodigoDisciplina(), response.getBody().getCodigo());
        assertEquals(rd.getNome(), response.getBody().getNome());
        assertEquals(rd.getCodigoCurso(), response.getBody().getCurso().getCodigo());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void postDisciplinaComCodigoCursoZeroDaErro() {
        RequestDisciplina rd = Fixture.from(RequestDisciplina.class).gimme("valido");
        ResponseEntity<Disciplina> response = restTemplate.postForEntity("/disciplinas", rd, Disciplina.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postDisciplinaSemCodigoDisciciplinaDaErro() {
        RequestDisciplina rd = Fixture.from(RequestDisciplina.class).gimme("valido");
        rd.setCodigoCurso(31L);
        rd.setCodigoDisciplina(null);
        ResponseEntity<Disciplina> response = restTemplate.postForEntity("/disciplinas", rd, Disciplina.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postDisciplinaSemNomeDaErro() {
        RequestDisciplina rd = Fixture.from(RequestDisciplina.class).gimme("valido");
        rd.setCodigoCurso(31L);
        rd.setNome(null);
        ResponseEntity<Disciplina> response = restTemplate.postForEntity("/disciplinas", rd, Disciplina.class);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void postDisciplinaNuloDaErro() {
        ResponseEntity<Disciplina> response = restTemplate.postForEntity("/disciplinas", null, Disciplina.class);
        assertEquals(HttpStatus.UNSUPPORTED_MEDIA_TYPE, response.getStatusCode());
    }
}
