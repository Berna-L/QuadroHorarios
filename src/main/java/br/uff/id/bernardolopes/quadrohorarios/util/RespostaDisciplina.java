/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.util;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class RespostaDisciplina {

    private String codigoDisciplina;
    private String nome;
    private Long codigoCurso;

    public RespostaDisciplina(String codigoDisciplina, String nome, Long codigoCurso) {
        this.codigoDisciplina = codigoDisciplina;
        this.nome = nome;
        this.codigoCurso = codigoCurso;
    }

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

    public Long getCodigoCurso() {
        return codigoCurso;
    }

    public void setCodigoCurso(Long codigoCurso) {
        this.codigoCurso = codigoCurso;
    }

    @Override
    public String toString() {
        return "RespostaPost{" + "codigoDisciplina=" + codigoDisciplina + ", nome=" + nome + ", codigoCurso=" + codigoCurso + '}';
    }

}
