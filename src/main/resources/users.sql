create table users
(
    id       serial
        primary key,
    name     varchar(50) not null,
    email    varchar(50) not null
        unique,
    password varchar(10) not null,
    gender   varchar(10)
);

alter table users
    owner to postgres;

