create table if not exists manager
(
    no   bigserial not null primary key,
    label varchar(50),
    param1 varchar(50)
);

