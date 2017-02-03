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
import javax.persistence.ManyToOne;

/**
 *
 * @author bernardolopes at id.uff.br
 */

@Entity
public class Turma implements Serializable {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String codigo;
    
    private String anosemestre;
    
    @ManyToOne
    private Disciplina disciplina;
    

    public Turma() {
    }
    
    public Turma(String codigo, String anosemestre, Disciplina disciplina) {
        this.codigo = codigo;
        this.anosemestre = anosemestre;
        this.disciplina = disciplina;
    }

    public Long getId() {
        return id;
    }
    
    public String getCodigo() {
        return codigo;
    }

    public Disciplina getDisciplina() {
        return disciplina;
    }

    public String getAnosemestre() {
        return anosemestre;
    }
}
