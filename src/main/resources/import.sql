insert into curso (codigo, nome) values ("031", "Ciência da Computação");
insert into curso (codigo, nome) values ("032", "Matemática");
insert into curso (codigo, nome) values ("033", "Física");

insert into disciplina (codigo, nome, curso) values ("TCC00173", "Programação I", "031");
insert into disciplina (codigo, nome, curso) values ("TCC00174", "Programação II", "031");
insert into disciplina (codigo, nome, curso) values ("GMA00108", "Cálculo I-A", "032");
insert into disciplina (codigo, nome, curso) values ("GFI00158", "Física I", "033");

insert into turma (codigo, disciplina) values ("A1", "TCC00173");
insert into turma (codigo, disciplina) values ("B1", "TCC00173");
insert into turma (codigo, disciplina) values ("A1", "TCC00174");
insert into turma (codigo, disciplina) values ("E1", "GMA00108");
insert into turma (codigo, disciplina) values ("G1", "GMA00108");
insert into turma (codigo, disciplina) values ("H1", "GMA00108");
insert into turma (codigo, disciplina) values ("B1", "GFI00158");