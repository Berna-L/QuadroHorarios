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
public class Curso implements Serializable {
    
    @Id 
    private final String curso;
    
    private final String nome;

    public Curso(String curso, String nome) {
        this.curso = curso;
        this.nome = nome;
    }

    public String getCurso() {
        return curso;
    }

    public String getNome() {
        return nome;
    }
}
