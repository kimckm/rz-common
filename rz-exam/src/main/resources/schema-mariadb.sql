drop table if exists t_choice;

create table t_choice (
  id int primary key,
  question varchar(255) comment '题目',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4;

insert into t_choice(id, question, create_at) values('1', '中国有多少个省份?', '2020-12-27 11:53:00');
