insert into curso (codigo, nome) values ('031', 'Ciência da Computação');
insert into curso (codigo, nome) values ('032', 'Matemática');
insert into curso (codigo, nome) values ('033', 'Física');

insert into disciplina (codigo, nome, curso_codigo) values ('TCC00173', 'Programação I', '031');
insert into disciplina (codigo, nome, curso_codigo) values ('TCC00174', 'Programação II', '031');
insert into disciplina (codigo, nome, curso_codigo) values ('GMA00108', 'Cálculo I-A', '032');
insert into disciplina (codigo, nome, curso_codigo) values ('GFI00158', 'Física I', '033');

insert into turma (codigo, disciplina_id) values ('A1', 1);
insert into turma (codigo, disciplina_id) values ('B1', 1);
insert into turma (codigo, disciplina_id) values ('A1', 2);
insert into turma (codigo, disciplina_id) values ('E1', 3);
insert into turma (codigo, disciplina_id) values ('G1', 3);
insert into turma (codigo, disciplina_id) values ('H1', 3);
insert into turma (codigo, disciplina_id) values ('B1', 4);