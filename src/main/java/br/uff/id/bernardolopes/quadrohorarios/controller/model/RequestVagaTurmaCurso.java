/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller.model;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class RequestVagaTurmaCurso {
    
    private Long idTurma;
    
    private Long idCurso;
    
    private Integer vagas;

    public RequestVagaTurmaCurso() {
    }

    public Long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(Long idTurma) {
        this.idTurma = idTurma;
    }

    public Long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(Long idCurso) {
        this.idCurso = idCurso;
    }

    public Integer getVagas() {
        return vagas;
    }

    public void setVagas(Integer vagas) {
        this.vagas = vagas;
    }
    
    public boolean isValid() {
        return idTurma > -1 && idCurso > -1 && vagas > 0; 
    }
}
