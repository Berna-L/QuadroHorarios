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
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.model.VagaTurmaCurso;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class VagaTurmaCursoTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(VagaTurmaCurso.class).addTemplate("valido", new Rule() {
            {
                add("turma", one(Turma.class, "valido"));
                add("curso", one(Curso.class, "valido"));
                add("vagas", random(Integer.class, 1, 80));
            }
        });
        Fixture.of(VagaTurmaCurso.class).addTemplate("turma-disciplina-fixas", new Rule() {
            {
                add("turma", one(Turma.class, "turma-disciplina-fixas"));
                add("curso", one(Curso.class, "valido"));
                add("vagas", random(Integer.class, 1, 80));
            }
        });
        Fixture.of(VagaTurmaCurso.class).addTemplate("curso-fixo", new Rule() {
            {
                add("turma", one(Turma.class, "curso-fixo"));
                add("curso", one(Curso.class, "curso-fixo"));
                add("vagas", random(Integer.class, 1, 80));

            }
        });
    }

}
