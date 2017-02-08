/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.repository;

import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.VagaTurmaCurso;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public interface VagaTurmaCursoDAO extends JpaRepository<VagaTurmaCurso, Long> {

    public List<VagaTurmaCurso> findByTurma(Turma turma);

    public List<VagaTurmaCurso> findByTurmaAndCurso(Turma turma, Curso curso);

    public List<VagaTurmaCurso> findByCurso(Curso curso);

    @Query("select curso, count(*)\n"
            + "from VagaTurmaCurso vtc inner join vtc.curso as curso\n"
            + "GROUP BY curso.nome")
    public List<Object[]> getCountByCurso();

    @Query("select t.anoSemestre, count(*)\n"
            + "from Turma t\n"
            + "where t in (\n"
            + "select distinct turmaInner\n"
            + "from VagaTurmaCurso vtc inner join vtc.turma as turmaInner\n"
            + ")group by t.anoSemestre")
    public List<Object[]> getCountByAnoSemestre();

    @Query("select t.anoSemestre, count(*)\n"
            + "from Turma t inner join t.disciplina as disciplina\n"
            + "where disciplina = ?1\n"
            + "group by t.anoSemestre")
    public List<Object[]> getVagasByAnoSemestreForDisciplina(Disciplina d);
    
}
