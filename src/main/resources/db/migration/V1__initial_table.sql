create table login_tb (
            id  SERIAL,
            firstName VARCHAR(255) not null,
            lastName VARCHAR(255) not null,
            email varchar(255) not null,
            password varchar(255) not null,
            primary key (id),
            unique (id)
);