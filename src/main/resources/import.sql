insert into curso (codigo, nome) values ('031', 'Ciência da Computação');
insert into curso (codigo, nome) values ('032', 'Matemática');
insert into curso (codigo, nome) values ('033', 'Física');

insert into disciplina (codigo, nome, curso_codigo) values ('TCC00173', 'Programação I', '031');
insert into disciplina (codigo, nome, curso_codigo) values ('TCC00174', 'Programação II', '031');
insert into disciplina (codigo, nome, curso_codigo) values ('GMA00108', 'Cálculo I-A', '032');
insert into disciplina (codigo, nome, curso_codigo) values ('GFI00158', 'Física I', '033');

insert into turma (codigo, ano_semestre, disciplina_id) values ('A1', '2016_1', 1);
insert into turma (codigo, ano_semestre, disciplina_id) values ('B1', '2016_2', 1);
insert into turma (codigo, ano_semestre, disciplina_id) values ('A1', '2016_1', 2);
insert into turma (codigo, ano_semestre, disciplina_id) values ('E1', '2016_1', 3);
insert into turma (codigo, ano_semestre, disciplina_id) values ('G1', '2015_1', 3);
insert into turma (codigo, ano_semestre, disciplina_id) values ('H1', '2015_2', 3);
insert into turma (codigo, ano_semestre, disciplina_id) values ('B1', '2014_2', 4);

insert into vaga_turma_curso (turma_id, curso_codigo, vagas) values (1, 31, 40);
insert into vaga_turma_curso (turma_id, curso_codigo, vagas) values (2, 31, 30);
insert into vaga_turma_curso (turma_id, curso_codigo, vagas) values (3, 31, 15);
insert into vaga_turma_curso (turma_id, curso_codigo, vagas) values (3, 32, 25);
insert into vaga_turma_curso (turma_id, curso_codigo, vagas) values (3, 33, 10);
insert into vaga_turma_curso (turma_id, curso_codigo, vagas) values (4, 31, 10);
insert into vaga_turma_curso (turma_id, curso_codigo, vagas) values (4, 33, 35);