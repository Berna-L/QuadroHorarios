/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller.model;

import br.uff.id.bernardolopes.quadrohorarios.model.Curso;

/**
 *
 * @author bernardo
 */
public class ResponseVagasInscritos {
    
    private Long curso;
    
    private Integer vagas;
    
    private Integer inscritos;    

    public ResponseVagasInscritos(Long curso, Integer vagas, Integer inscritos) {
        this.curso = curso;
        this.vagas = vagas;
        this.inscritos = inscritos;
    }

    public Long getCurso() {
        return curso;
    }

    public void setCurso(Long curso) {
        this.curso = curso;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }

    public Integer getInscritos() {
        return inscritos;
    }

    public void setInscritos(Integer inscritos) {
        this.inscritos = inscritos;
    }
    
    
}
