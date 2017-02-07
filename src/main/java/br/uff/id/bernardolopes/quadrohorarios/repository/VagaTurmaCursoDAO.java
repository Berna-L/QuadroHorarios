/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.repository;

import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.VagaTurmaCurso;
import java.io.Serializable;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public interface VagaTurmaCursoDAO extends JpaRepository<VagaTurmaCurso, Long>{
    public List<VagaTurmaCurso> findByTurma(Turma turma);
    
    public List<VagaTurmaCurso> findByTurmaAndCurso(Turma turma, Curso curso);
    
    public List<VagaTurmaCurso> findByCurso(Curso curso);
}
