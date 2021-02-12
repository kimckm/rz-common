create table t_choice (
  id int primary key,
  question varchar(255) comment '题目',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4 comment='选择题';

create table t_exam (
  id bigint unsigned primary key,
  title varchar(255) comment '考试标题',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4 comment='考试表';

create table t_exam_question (
  id bigint unsigned primary key,
  exam_id bigint,
  question_id bigint
) engine=InnoDB default charset=utf8mb4 comment='考试题目表';

create table t_completion (
  id bigint unsigned primary key,
  question varchar(500) comment '题目',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4 comment='填空题';

create table t_completion_correct (
 id bigint unsigned primary key,
 completion_id bigint unsigned not null comment '所属填空题',
 code varchar(20) comment '占位代码',
 expected varchar(20) comment '答案'
) engine=InnoDB default charset=utf8mb4 comment='填空题正确答案';

create table t_completion_audio (
 id bigint unsigned primary key,
 completion_id bigint unsigned not null comment '所属填空题',
 name varchar(20) comment '音频名称',
 src varchar(255) comment '资源地址'
) engine=InnoDB default charset=utf8mb4 comment='填空题音频表';
