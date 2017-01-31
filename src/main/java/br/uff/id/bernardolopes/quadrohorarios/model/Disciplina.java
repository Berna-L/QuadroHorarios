/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Entity
public class Disciplina implements Serializable {
    
    @Id
    private final String codigo;
    
    private final String nome;
    
    @ManyToOne
    private final Curso curso;
    
    @OneToMany(mappedBy = "disciplina", targetEntity = Disciplina.class)
    private final List<Turma> turmas;

    public Disciplina(String codigo, String nome, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.curso = curso;
        this.turmas = new ArrayList<>();
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public Curso getCurso() {
        return curso;
    }

    public List<Turma> getTurmas() {
        return turmas;
    }
    
    
}
