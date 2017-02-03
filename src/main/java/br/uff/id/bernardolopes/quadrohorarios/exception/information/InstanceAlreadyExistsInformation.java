/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.exception.information;

import java.net.URL;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class InstanceAlreadyExistsInformation {
    
    private final String reason;
    private final String requestURL;

    public InstanceAlreadyExistsInformation(String reason, String requestURL) {
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
