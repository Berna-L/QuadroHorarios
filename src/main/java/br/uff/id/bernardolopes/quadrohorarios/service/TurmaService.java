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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Service
public class TurmaService {

    @Autowired
    private TurmaDAO turmaDAO;

    @Autowired
    private DisciplinaDAO disciplinaDAO;

    public TurmaService() {
    }

    public Turma criarTurma(String codigoTurma, Disciplina disciplina) throws InstanceAlreadyExistsException {
        if (turmaDAO.findByCodigoAndDisciplina(codigoTurma, disciplina).isEmpty()) {
            if (disciplina == null) {
                return null;
            }
            Turma t = new Turma(codigoTurma, disciplina);
            turmaDAO.save(t);
            return t;
        } else {
            throw new InstanceAlreadyExistsException();
        }


    }

    public Turma criarTurma(String codigoTurma, String codigoDisciplina) throws InstanceAlreadyExistsException {
        Disciplina d = disciplinaDAO.findByCodigo(codigoDisciplina).get(0);
        return criarTurma(codigoTurma, d);
    }
}
