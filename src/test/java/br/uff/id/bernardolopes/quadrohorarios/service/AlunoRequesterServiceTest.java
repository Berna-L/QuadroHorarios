/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.loader.FixtureFactoryLoader;
import br.uff.id.bernardolopes.quadrohorarios.model.Aluno;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.net.URL;
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
import static org.mockito.Mockito.*;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AlunoRequesterServiceTest {
    
    private ObjectMapper mapper;
    private static final String REST_URL = "http://localhost:8080/test/";
    
    private AlunoRequesterService service;
    
    @Before
    public void setUp(){
        mapper = mock(ObjectMapper.class);
        FixtureFactoryLoader.loadTemplates("br.uff.id.bernardolopes.quadrohorarios.template");
        service = new AlunoRequesterService(mapper);
    }
    
    //Tá, tem esse teste, mas... ele não é nada a ver?
    @Test
    public void resultadoOK() throws IOException{
        List<Aluno> alunos = Fixture.from(Aluno.class).gimme(2, "valido");
        Turma turma = Fixture.from(Turma.class).gimme("valido");
        Map<Curso, List<Aluno>> mapaEsperado = new HashMap<>();
        for (Aluno aluno : alunos){
            Curso curso = aluno.getCurso();
            if (mapaEsperado.get(curso) == null){
                mapaEsperado.put(curso, new ArrayList<>());
            }
            mapaEsperado.get(curso).add(aluno);
        }
        URL url = new URL(REST_URL + turma.getId());
        when(mapper.readValue(url, Map.class)).thenReturn(mapaEsperado);
        Map<Curso, List<Aluno>> mapa = service.getListaAlunosPorCursoEmTurma(turma, url);
        assertEquals(mapaEsperado, mapa);
    }
}
