/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.id.uff.bernardolopes.quadrohorarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * @author bernardolopes at id.uff.br
 */

@SpringBootApplication
//@ComponentScan({"br.uff.id.bernardolopes.quadrohorarios.repository", "br.uff.id.bernardolopes.quadrohorarios.model"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
    
}