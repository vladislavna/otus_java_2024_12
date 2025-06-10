create sequence client_SEQ start with 1 increment by 1;

create table client
(
    id   bigint not null primary key,
    name varchar(50),
    address_id bigint
);

create table address
(
    id     bigserial not null primary key,
    street varchar(50)
);

create table phone
(
    id        bigserial not null primary key,
    number    varchar(50),
    client_id bigint
);
