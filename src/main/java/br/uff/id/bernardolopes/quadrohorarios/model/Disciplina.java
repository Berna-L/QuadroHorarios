/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
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
    @GeneratedValue
    private Long id;
    
    private String codigo;
    
    private String nome;
    
    @ManyToOne
    private Curso curso;
    
//    @OneToMany(mappedBy = "disciplina", targetEntity = Turma.class)
//    private List<Turma> turmas;

    public Disciplina() {
//        this.turmas = new ArrayList<>();
    }

    public Disciplina(String codigo, String nome, Curso curso) {
        this.codigo = codigo;
        this.nome = nome;
        this.curso = curso;
    }

    public Long getId(){
        return id;
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
    
    @Override
    public boolean equals(Object obj){
        if (obj == null){
            return false;
        }
        if (obj == this){
            return true;
        }
        if (!(obj instanceof Disciplina)){
            return false;
        }
        if (this == null){
            System.out.println("Eu sou nulo?!?!?!?!?");
        }
        Disciplina other = (Disciplina) obj;
        return (this.getId() == other.getId() || this.getCodigo().equals(other.getCodigo()));
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 67 * hash + Objects.hashCode(this.id);
        hash = 67 * hash + Objects.hashCode(this.codigo);
        return hash;
    }

}
