

drop table if exists students;

create  table students (

          studentId bigint not null,
          firstname varchar(50) not null,
          lastname varchar(50) not null,
          email varchar(50) not null,
          password varchar(50) not null,

          PRIMARY KEY(studentId)
);