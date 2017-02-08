/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonTypeId;
import com.fasterxml.jackson.annotation.JsonValue;
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
public class Turma implements Serializable, Comparable<Turma> {
    
    @Id
    @GeneratedValue
    private Long id;
    
    private String codigo;
    
    private String anoSemestre;
    
    @ManyToOne
//    @JsonIgnoreProperties(value = {"codigo", "nome", "curso"})
    private Disciplina disciplina;
    

    public Turma() {
    }
    
    public Turma(String codigo, String anoSemestre, Disciplina disciplina) {
        this.codigo = codigo;
        this.anoSemestre = anoSemestre;
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

    public String getAnoSemestre() {
        return anoSemestre;
    }

    @Override
    public int compareTo(Turma other) {
         if (this.getId() == other.getId() || (this.getCodigo().equals(other.getCodigo()) && this.getDisciplina().equals(other.getDisciplina()) && this.getAnoSemestre().equals(other.getAnoSemestre()))){
             return 0;
         }
         return 1;
    }
}
