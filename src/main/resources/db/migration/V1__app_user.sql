drop table if exists app_user;

create  table app_user (

          studentId bigint not null,
          firstname varchar(49) not null,
          lastname varchar(49) not null,
          email varchar(49) not null,
          password varchar(49) not null,

          PRIMARY KEY(studentId)
);