/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Entity
public class VagaTurmaCurso implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    private Turma turma;
    
    @ManyToOne
    private Curso curso;
    
    private Integer vagas;

    public VagaTurmaCurso() {
    }

    public VagaTurmaCurso(Turma turma, Curso curso, Integer vagas) {
        this.turma = turma;
        this.curso = curso;
        this.vagas = vagas;
    }

    public Long getId() {
        return id;
    }

    public Turma getTurma() {
        return turma;
    }

    public Curso getCurso() {
        return curso;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }
    
}
