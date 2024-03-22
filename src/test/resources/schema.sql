create table if not exists users (
    id bigserial not null,
    name varchar(30) not null,
    primary key (id)
);