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

create table modulos (
                         id bigserial primary key,
                         curso_id bigint not null references cursos(id),
                         ordem integer not null,
                         titulo varchar(200) not null,
                         conteudo text,
                         tipo_conteudo varchar(50),
                         obrigatorio boolean not null default true,
                         xp_modulo integer
);

create table materiais (
                           id bigserial primary key,
                           modulo_id bigint not null references modulos(id),
                           nome_arquivo varchar(255) not null,
                           tipo_arquivo varchar(50) not null,
                           url_storage varchar(255) not null,
                           tamanho bigint
);

-- Adiciona um índice para otimizar a busca de módulos por curso
create index idx_modulos_curso_id on modulos(curso_id);
create index idx_materiais_modulo_id on materiais(modulo_id);