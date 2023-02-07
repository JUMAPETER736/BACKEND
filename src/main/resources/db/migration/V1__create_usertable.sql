

create sequence id_sequence_generator start 1 increment 1;

create table users (


        id int not null,
        firstName varchar(255) not null,
        lastName  varchar(255) not null,
        email varchar(255) not null,
        password varchar(255) not null,
        primary key(id)

);

