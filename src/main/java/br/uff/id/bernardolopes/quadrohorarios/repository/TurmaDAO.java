/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.repository;

import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public interface TurmaDAO extends JpaRepository<Turma, Long>{
        public List<Turma> findByCodigoAndDisciplina(String codigo, Disciplina disciplina);

}
