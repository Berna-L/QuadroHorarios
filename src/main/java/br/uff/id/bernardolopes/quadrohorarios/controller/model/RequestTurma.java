/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.controller.model;

import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import java.io.Serializable;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class RequestTurma implements Serializable {

    private String codigoTurma;

    private String codigoDisciplina;

    private String anoSemestre;

    public RequestTurma() {
    }

    public String getCodigoTurma() {
        return codigoTurma;
    }

    public void setCodigoTurma(String codigo) {
        this.codigoTurma = codigo;
    }

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigo) {
        this.codigoDisciplina = codigo;
    }

    public boolean isValid() {
        return codigoTurma != null && codigoDisciplina != null && anoSemestre != null;
    }

    public String getAnoSemestre() {
        return anoSemestre;
    }

    public void setAnoSemestre(String anoSemestre) {
        this.anoSemestre = anoSemestre;
    }

}
