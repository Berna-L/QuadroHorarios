/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.model;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author sti
 */
@Entity
public class Aluno implements Serializable {

    private static final Long serialVersionUID = 1L;
   
    private String nome;
    @Id
    private Long matricula;
    
    @ManyToOne
    private Curso curso;
    
    public Aluno() {}

    public Aluno(String nome, Long matricula) {
        this.nome = nome;
        this.matricula = matricula;
    }

    public String getNome() {
        return nome;
    }

    public Long getMatricula() {
        return matricula;
    }

    public Curso getCurso() {;
        return curso;
    }

}
