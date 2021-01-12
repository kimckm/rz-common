drop table if exists t_choice;
create table t_choice (
  id int primary key,
  question varchar(255) comment '题目',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4;
insert into t_choice(id, question, create_at) values(1, '中国有多少个省份?', '2020-12-27 11:53:00');

drop table if exists t_exam;
create table t_exam (
  id int primary key,
  title varchar(255) comment '考试标题',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4;
insert into t_exam(id, title, create_at) values(1, '模拟考试', '2020-12-27 11:53:00');

drop table if exists t_completion;
create table t_completion (
  id int primary key,
  question varchar(255) comment '题目',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4;
insert into t_completion(id, question, create_at) values(1, '苹果的英文单词是{s}。', '2020-12-27 11:53:00');

drop table if exists t_completion_option;
create table t_completion_option (
  id int primary key,
  fillId int comment '所属填空题',
  code varchar(20) comment '占位代码',
  answer varchar(20) comment '答案'
) engine=InnoDB default charset=utf8mb4;
insert into t_completion_option(id, fillId, code, answer) values(1, 1, 's', '/^apple$/i');
