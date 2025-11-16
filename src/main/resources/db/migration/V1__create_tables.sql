create table categorias (
                            id bigserial primary key,
                            codigo varchar(30) unique not null,
                            nome varchar(120) not null,
                            descricao text,
                            cor_hex varchar(7)
);

create table instrutor (
                           id bigserial primary key,
                           nome varchar(120) not null
);

create table cursos (
                        id bigserial primary key,
                        codigo varchar(30) unique not null,
                        titulo varchar(200) not null,
                        descricao text,
                        categoria_id bigint not null references categorias(id),
                        instrutor_id bigint not null references instrutor(id),
                        duracao_estimada integer,
                        xp_oferecido integer not null default 0,
                        nivel_dificuldade varchar(30) not null,
                        ativo boolean not null default true,
                        pre_requisitos text
);