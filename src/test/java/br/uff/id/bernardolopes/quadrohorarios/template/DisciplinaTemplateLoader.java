/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.uff.id.bernardolopes.quadrohorarios.model.Curso;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class DisciplinaTemplateLoader implements TemplateLoader {
    @Override
    public void load() {
        Fixture.of(Disciplina.class).addTemplate("valido", new Rule() {
            {
                add("codigo", regex("TES\\d{5}"));
                add("nome", regex("Cálculo \\d{3}"));
                add("curso", one(Curso.class, "valido"));
            }
        });
    }

}
