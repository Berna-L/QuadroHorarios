/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.uff.id.bernardolopes.quadrohorarios.model.Aluno;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class AlunoTemplateLoader implements TemplateLoader {
    @Override
    public void load(){
        Fixture.of(Aluno.class).addTemplate("valido", new Rule() {{
            add("nome", name());
            add("matricula", regex("1[0-6][1-2]031\\d{3}"));
            add("curso", one(Curso.class, "valido"));
        }});
    }
}