/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.repository.CursoDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.DisciplinaDAO;
import br.uff.id.bernardolopes.quadrohorarios.repository.TurmaDAO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
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

    @Autowired
    private DisciplinaDAO disciplinaDAO;

    @Autowired
    private CursoDAO cursoDAO;

    @Autowired
    private DisciplinaService service;

    private static final String CODIGO_DISCIPLINA = "TES40404";
    private static final String NOME_DISCIPLINA = "Testes de Configuração";
    
    private static final String CODIGO_DISCIPLINA_2 = "TES42424";
    private static final String NOME_DISCIPLINA_2 = "Testes de Universo";

    private static final String CODIGO_DISCIPLINA_EXISTENTE = "TCC00173";
    private static final String NOME_DISCIPLINA_EXISTENTE = "Isso aqui nem importa";

    private static final long CODIGO_CURSO = 31L;
    private static final long CODIGO_CURSO_INEXISTENTE = 0L;

    @Test
    public void insereNoBancoComObjetoCurso() {
        long size = disciplinaDAO.count();
        Curso c = cursoDAO.findOne(CODIGO_CURSO);
        Disciplina d = service.criarDisciplina(CODIGO_DISCIPLINA, NOME_DISCIPLINA, c);
        assertEquals(CODIGO_DISCIPLINA, d.getCodigo());
        assertEquals(NOME_DISCIPLINA, d.getNome());
        assertEquals(c, d.getCurso());
        assertEquals(size + 1, disciplinaDAO.count());
    }

    @Test
    public void insereNoBancoComCodigoCurso() {
        long size = disciplinaDAO.count();
        Disciplina d = service.criarDisciplina(CODIGO_DISCIPLINA_2, NOME_DISCIPLINA_2, CODIGO_CURSO);
        assertEquals(CODIGO_DISCIPLINA_2, d.getCodigo());
        assertEquals(NOME_DISCIPLINA_2, d.getNome());
        assertEquals(CODIGO_CURSO, d.getCurso().getCodigo());
        assertEquals(size + 1, disciplinaDAO.count());
    }

    @Test(expected = InstanceAlreadyExistsException.class)
    public void insereNoBancoJaExiste() {
        service.criarDisciplina(CODIGO_DISCIPLINA_EXISTENTE, NOME_DISCIPLINA_EXISTENTE, CODIGO_CURSO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCodigoNuloDaErro() {
        service.criarDisciplina(null, NOME_DISCIPLINA, CODIGO_CURSO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComNomeNuloDaErro() {
        service.criarDisciplina(CODIGO_DISCIPLINA, null, CODIGO_CURSO);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCursoNuloDaErro() {
        service.criarDisciplina(null, NOME_DISCIPLINA, (Curso) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCursoInexistenteDaErro() {
        service.criarDisciplina(CODIGO_DISCIPLINA, NOME_DISCIPLINA, CODIGO_CURSO_INEXISTENTE);
    }
}
