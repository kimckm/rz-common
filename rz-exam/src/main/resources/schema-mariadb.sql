drop table if exists t_choice;
create table t_choice (
  id int primary key,
  question varchar(255) comment '题目',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4 comment='选择题';

drop table if exists t_exam;
create table t_exam (
  id int primary key,
  title varchar(255) comment '考试标题',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4 comment='考试表';

drop table if exists t_completion;
create table t_completion (
  id int primary key auto_increment,
  question varchar(500) comment '题目',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4 comment='填空题';

drop table if exists t_completion_correct;
create table t_completion_correct (
 id int primary key auto_increment,
 completion_id int comment '所属填空题',
 code varchar(20) comment '占位代码',
 expected varchar(20) comment '答案'
) engine=InnoDB default charset=utf8mb4 comment='填空题正确答案';

drop table if exists t_completion_audio;
create table t_completion_audio (
 id int primary key auto_increment,
 completion_id int comment '所属填空题',
 name varchar(20) comment '音频名称',
 src varchar(255) comment '资源地址'
) engine=InnoDB default charset=utf8mb4 comment='填空题音频表';
