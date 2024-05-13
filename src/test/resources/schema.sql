create table if not exists users (
    id bigserial not null,
    name varchar(30) not null,
    primary key (id)
);

create table if not exists foo (
    id bigserial not null,
    name varchar(30) not null,
    primary key (id)
);

create table if not exists foo_ext (
    id bigserial not null,
    ext_value varchar(10) not null,
    foo_id bigserial not null,
    FOREIGN KEY (foo_id) REFERENCES foo (id)
);