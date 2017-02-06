/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.exception.information;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class IllegalArgumentInformation {

    private final String reason;
    private final String requestURL;

    public IllegalArgumentInformation(String reason, String requestURL) {
        this.reason = reason;
        this.requestURL = requestURL;
    }

    public String getReason() {
        return reason;
    }

    public String getRequestURL() {
        return requestURL;
    }
}
