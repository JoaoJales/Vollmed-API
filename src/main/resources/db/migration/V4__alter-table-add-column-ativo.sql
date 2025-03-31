alter table doctors add ativo tinyint;
update doctors set ativo = 1;

alter table patients add ativo tinyint;
update patients set ativo = 1;