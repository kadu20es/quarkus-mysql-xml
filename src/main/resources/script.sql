start transaction;

create table `boletoerp`.`cliente`
(
    id          bigint not null auto_increment primary key,
    nome        varchar(100) not null,
    tipoPessoa  varchar(1)   not null,
    email       varchar(100) not null,
    processado	boolean default false,
    createdAt timestamp not null default current_timestamp(),
    updatetedAt timestamp not null default current_timestamp()
);

create table `boletoerp`.`endereco`
(
    id          bigint not null auto_increment primary key,
    logradouro  varchar(100) not null,
    numero      varchar(100) not null,
    complemento varchar(100),
    bairro      varchar(100) not null,
    municipio   varchar(100) not null,
    uf          varchar(2) not null,
    cep         varchar(9) not null,
    createdAt timestamp not null default current_timestamp(),
    updatetedAt timestamp not null default current_timestamp()
);


create table `boletoerp`.`cliente_endereco`
(
	idCLiente bigint not null,
    idEndereco bigint not null,
    primary key (idCliente, idEndereco),
    constraint fk_idCliente foreign key (idCliente)
    references cliente(id) on delete cascade,
    constraint fk_idEndereco foreign key (idEndereco)
    references endereco(id) on delete cascade
);

commit;

// -----------------------------

/* insere em duas tabelas ao mesmo tempo */
START transaction;

INSERT INTO `boletoerp`.cliente (nome, tipoPessoa, email) values ("Fulano de tal", "M", "fulanodetal@gmail.com");
SET @cliente_id = LAST_INSERT_ID();

INSERT INTO `boletoerp`.endereco
(logradouro, numero, complemento, bairro, municipio, uf, cep)

VALUES ("Rua da felicidade", "69", "Bl 7 ap 211", "Colina das Nuvens", "Serra", "ES", "29000000");
SET @endereco_id = last_insert_id();

INSERT INTO `boletoerp`.cliente_endereco (idCliente, idEndereco)
VALUES (@cliente_id, @endereco_id);

COMMIT;


// -------------------------------------------


SELECT
	c.nome, c.tipoPessoa, c.email, e.logradouro, e.numero, e.complemento, e.bairro, e.municipio, e.uf, e.cep, c.processado
FROM `boletoerp`.`cliente` c
JOIN `boletoerp`.`endereco` e, `boletoerp`.`cliente_endereco` ce
WHERE c.id = ce.idCliente
AND e.id = ce.idEndereco;