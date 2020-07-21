drop table if exists t_web;

create table t_web (
  id int primary key auto_increment,
  name varchar(100)
) engine=InnoDB default charset=utf8mb4;