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

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class CursoTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Curso.class).addTemplate("valido", new Rule() {
            {
                add("codigo", random(Long.class, range(10L, 70L)));
                add("nome", random("Ciência da Computação", "Medicina", "Letras", "Matemática"));
            }
        });
        Fixture.of(Curso.class).addTemplate("curso-fixo", new Rule() {
            {
                add("codigo", 900L);
                add("nome", "Curso de Fixação");
            }
        });

    }
}
