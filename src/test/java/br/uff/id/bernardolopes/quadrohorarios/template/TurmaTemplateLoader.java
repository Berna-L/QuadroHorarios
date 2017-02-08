/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.uff.id.bernardolopes.quadrohorarios.template;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import br.uff.id.bernardolopes.quadrohorarios.model.Disciplina;
import br.uff.id.bernardolopes.quadrohorarios.model.Turma;
import br.uff.id.bernardolopes.quadrohorarios.controller.model.RequestTurma;

/**
 *
 * @author bernardolopes at id.uff.br
 */
public class TurmaTemplateLoader implements TemplateLoader {

    @Override
    public void load() {
        Fixture.of(Turma.class).addTemplate("valido", new Rule() {
            {
                add("codigo", regex("[A-Z]{1}[0-9]{1}"));
                add("anoSemestre", regex("2016_1"));
                add("disciplina", one(Disciplina.class, "valido"));
            }
        });
        Fixture.of(Turma.class).addTemplate("turma-disciplina-fixas", new Rule() {
            {
                add("codigo", "A1");
                add("anoSemestre", regex("2016_2"));
                add("disciplina", one(Disciplina.class, "turma-disciplina-fixas"));
            }
        });
        Fixture.of(Turma.class).addTemplate("curso-fixo", new Rule() {
            {
                add("codigo", "[A-Z]{1}1");
                add("anoSemestre", regex("2017_1"));
                add("disciplina", one(Disciplina.class, "curso-fixo"));
            }
        });
        Fixture.of(RequestTurma.class).addTemplate("valido", new Rule() {
            {
                add("codigoTurma", regex("[A-Z]{1}[0-9]{1}"));
                add("anoSemestre", regex("2016_1"));
                add("codigoDisciplina", "TCC00173");
            }
        });
    }
}
