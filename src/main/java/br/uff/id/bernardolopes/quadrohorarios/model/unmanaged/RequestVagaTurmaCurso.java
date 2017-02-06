/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.model.unmanaged;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class RequestVagaTurmaCurso {
    
    private long idTurma;
    
    private long idCurso;
    
    private int vagas;

    public RequestVagaTurmaCurso() {
    }

    public long getIdTurma() {
        return idTurma;
    }

    public void setIdTurma(long idTurma) {
        this.idTurma = idTurma;
    }

    public long getIdCurso() {
        return idCurso;
    }

    public void setIdCurso(long idCurso) {
        this.idCurso = idCurso;
    }

    public int getVagas() {
        return vagas;
    }

    public void setVagas(int vagas) {
        this.vagas = vagas;
    }
    
    public boolean isValid() {
        return idTurma > -1 && idCurso > -1 && vagas > 0; 
    }
}
