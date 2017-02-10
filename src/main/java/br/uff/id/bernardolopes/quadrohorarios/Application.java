/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios;

import br.uff.id.bernardolopes.quadrohorarios.exception.InstanceAlreadyExistsException;
import br.uff.id.bernardolopes.quadrohorarios.exception.information.IllegalArgumentInformation;
import br.uff.id.bernardolopes.quadrohorarios.exception.information.InstanceAlreadyExistsInformation;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@SpringBootApplication
@ControllerAdvice
//@EntityScan(basePackageClasses ={Curso.class})
//@ComponentScan({"br.uff.id.bernardolopes.quadrohorarios.repository", "br.uff.id.bernardolopes.quadrohorarios.model"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @ExceptionHandler(InstanceAlreadyExistsException.class)
    public ResponseEntity<InstanceAlreadyExistsInformation>
            instanceAlreadyExistsExceptionHandler(HttpServletRequest request,
                    Exception ex) {
        InstanceAlreadyExistsInformation info = new InstanceAlreadyExistsInformation(ex.toString(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(info);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<IllegalArgumentInformation>
            illegalArgumentExceptionHandler(HttpServletRequest request,
                    Exception ex) {
        IllegalArgumentInformation info = new IllegalArgumentInformation(ex.toString(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(info);
    }
}
