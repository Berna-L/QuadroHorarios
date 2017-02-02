/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Entity
public class VagaTurmaCurso implements Serializable {
    
    @Id
    private long id;
    
    private Turma turma;
    
    private Curso curso;
    
    private int vagas;

    public VagaTurmaCurso() {
    }

    public VagaTurmaCurso(long id, Turma turma, Curso curso, int vagas) {
        this.id = id;
        this.turma = turma;
        this.curso = curso;
        this.vagas = vagas;
    }

    public long getId() {
        return id;
    }

    public Turma getTurma() {
        return turma;
    }

    public Curso getCurso() {
        return curso;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }
    
}
