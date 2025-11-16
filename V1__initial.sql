create table categorias (
                            id bigserial primary key,
                            codigo varchar(30) unique not null,
                            nome varchar(120) not null,
                            descricao text,
                            cor_hex varchar(7)
);

create table cursos (
                        id bigserial primary key,
                        codigo varchar(30) unique not null,
                        titulo varchar(200) not null,
                        descricao text,
                        categoria_id bigint not null references categorias(id),
                        instrutor_id bigint not null, -- referência ao MS Usuários/Instrutores
                        duracao_estimada integer,     -- horas
                        xp_oferecido integer not null,
                        nivel_dificuldade varchar(30) not null, -- Iniciante/Intermediário/Avançado
                        ativo boolean not null default true,
                        pre_requisitos text           -- lista de códigos/ids (normalizar é opcional)
);

create table modulos (
                         id bigserial primary key,
                         curso_id bigint not null references cursos(id) on delete cascade,
                         ordem integer not null,
                         titulo varchar(200) not null,
                         conteudo text,
                         tipo_conteudo varchar(30) not null, -- texto/video
                         obrigatorio boolean not null default true,
                         xp_modulo integer not null default 0
);

create table materiais (
                           id bigserial primary key,
                           modulo_id bigint not null references modulos(id) on delete cascade,
                           nome_arquivo varchar(255) not null,
                           tipo_arquivo varchar(100),
                           url_storage varchar(500) not null,
                           tamanho bigint
);

create unique index uq_modulos_curso_ordem on modulos(curso_id, ordem);
