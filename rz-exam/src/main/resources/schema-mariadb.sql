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
  correct varchar(255) comment '正确答案',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4;
insert into
  t_completion(id, question, correct, create_at)
values
  (1, '苹果的英文单词是{s}。', '[{"code":"s","expected":"/^apple$/i"}]', '2020-12-27 11:53:00'),
  (2, 'TCP传输控制协议(Transmission Control Protocol)，是一个面向{A}的协议，为用户进程提供可靠的{B}字节流。', '[{"code":"A","expected":"连接"},{"code":"B","expected":"全双工"}]', '2020-12-27 11:53:00')
;

--drop table if exists t_completion_option;
--create table t_completion_option (
--  id int primary key,
--  completion_id int comment '所属填空题',
--  code varchar(20) comment '占位代码',
--  expected varchar(20) comment '答案'
--) engine=InnoDB default charset=utf8mb4;
--insert into t_completion_option(id, completion_id, code, expected) values(1, 1, 's', '/^apple$/i');
