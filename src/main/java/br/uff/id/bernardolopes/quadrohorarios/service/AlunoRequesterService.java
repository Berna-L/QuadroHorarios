/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.service;

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
    private ObjectMapper mapper;

    public AlunoRequesterService(ObjectMapper mapper) {
        this.mapper = mapper;
    }
    
    public Map<Curso, List<Aluno>> getListaAlunosPorCursoEmTurma(Turma turma) throws MalformedURLException, ProtocolException, IOException {
        URL url = new URL(REST_URL + turma.getId());
        return getListaAlunosPorCursoEmTurma(turma, url);
    }
    
    public Map<Curso, List<Aluno>> getListaAlunosPorCursoEmTurma(Turma turma, URL url) throws IOException{
        Map<Curso, List<Aluno>> resultado = mapper.readValue(url, Map.class);
        return resultado;
    }
}
