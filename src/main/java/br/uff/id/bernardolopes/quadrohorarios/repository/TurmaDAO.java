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
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public interface TurmaDAO extends JpaRepository<Turma, Long> {

    public List<Turma> findByCodigoAndAnoSemestreAndDisciplina(String codigo, String anoSemestre, Disciplina disciplina);

    @Query("select count(*)\n"
            + "from Turma t inner join t.disciplina as disciplina\n"
            + "where disciplina =?1")
    public Long getQuantidadeTurmasForDisciplina(Disciplina d);

}
