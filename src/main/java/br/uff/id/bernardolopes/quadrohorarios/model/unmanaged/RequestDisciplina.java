/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.model.unmanaged;

import java.io.Serializable;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class RequestDisciplina implements Serializable {

    private String codigoDisciplina;
    private String nome;
    private long codigoCurso;
    
    public RequestDisciplina() {}

    public String getCodigoDisciplina() {
        return codigoDisciplina;
    }

    public void setCodigoDisciplina(String codigoDisciplina) {
        this.codigoDisciplina = codigoDisciplina;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public long getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(long codigoCurso) {
        this.codigoCurso = codigoCurso;
    }
    
    public boolean isValid() {
        return codigoDisciplina != null && nome != null && codigoCurso != 0;
    }
}
