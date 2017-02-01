/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.requester;

import br.uff.id.bernardolopes.quadrohorarios.model.Aluno;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

/**
 *
 * @author bernardolopes at id.uff.br
 */
@Service
public class AlunoRequesterService {

    private static final String REST_URL = "http://localhost:8080/fake/";

    public Map<Curso, List<Aluno>> getListaAlunosPorCursoEmTurma(Turma turma) throws MalformedURLException, ProtocolException, IOException {
        URL obj = new URL(REST_URL + turma.getId());
//        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
//
//        conn.setRequestMethod("GET");
//
//        BufferedReader in = new BufferedReader(
//                new InputStreamReader(conn.getInputStream()));
        ObjectMapper mapper = new ObjectMapper();
        
        Map<Curso, List<Aluno>> resultado = mapper.readValue(obj, Map.class);
        
        return resultado;
    }

}
