/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
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
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TurmaServiceTest {

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private DisciplinaDAO disciplinaDAO;

    @Autowired
    private TurmaService service;

    private static final String CODIGO_TURMA = "T1";
    private static final String CODIGO_TURMA_2 = "T2";

    private static final String CODIGO_TURMA_EXISTENTE = "A1";
    private static final String CODIGO_DISCIPLINA_EXISTENTE = "TCC00173";

    private static final String CODIGO_DISCIPLINA = "GMA00108";
    private static final String CODIGO_DISCIPLINA_INEXISTENTE = "TES99999";

    @Test
    public void insereNoBancoComObjetoDisciplina() {
        long size = turmaDAO.count();
        Disciplina d = disciplinaDAO.findByCodigo(CODIGO_DISCIPLINA).get(0);
        Turma t = service.criarTurma(CODIGO_TURMA, d);
        assertEquals(CODIGO_TURMA, t.getCodigo());
        assertEquals(d, t.getDisciplina());
        assertEquals(size + 1, turmaDAO.count());
    }

    @Test
    public void insereNoBancoComCodigoDisciplina() {
        long size = turmaDAO.count();
        Turma t = service.criarTurma(CODIGO_TURMA_2, CODIGO_DISCIPLINA);
        assertEquals(CODIGO_TURMA_2, t.getCodigo());
        assertEquals(CODIGO_DISCIPLINA, t.getDisciplina().getCodigo());
        assertEquals(size + 1, turmaDAO.count());
    }

    @Test(expected = InstanceAlreadyExistsException.class)
    public void insereNoBancoJaExiste() {
        service.criarTurma(CODIGO_TURMA_EXISTENTE, CODIGO_DISCIPLINA_EXISTENTE);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComCodigoNuloDaErro() {
        service.criarTurma(null, CODIGO_DISCIPLINA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComDisciplinaNuloDaErro() {
        service.criarTurma(CODIGO_TURMA, (Disciplina) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void insereNoBancoComDisciplinaInexistenteDaErro() {
        service.criarTurma(CODIGO_TURMA, CODIGO_DISCIPLINA_INEXISTENTE);
    }
}
