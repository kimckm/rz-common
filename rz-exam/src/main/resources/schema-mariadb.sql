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
  id int primary key auto_increment,
  question varchar(255) comment '题目',
  correct varchar(255) comment '正确答案',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4;
insert into
  t_completion(question, correct, create_at)
values (
  'TCP传输控制协议(Transmission Control Protocol)，是一个面向{A}的协议，为用户进程提供可靠的{B}字节流。',
  '[{"code":"A","expected":"连接"},{"code":"B","expected":"全双工"}]',
  '2020-12-27 11:53:00'
), (
  'TCP含有用于{A}估算客户和服务器之间的{B}时间（round-trip time，RTT）的算法，以便它知道等待一个确认需要多少时间。',
  '[{"code":"A","expected":"动态"},{"code":"B","expected":"往返"}]',
  '2020-12-27 11:53:00'
), (
  '{A}是TCP传递给IP的数据单元。',
  '[{"code":"A","expected":"分节"}]',
  '2020-12-27 11:53:00'
), (
  'Tesco, Asda and Waitrose have become the latest supermarkets to say they will {A} {B} to shoppers who do not wear face masks unless they are medically exempt.',
  '[{"code":"A","expected":"deny"},{"code":"B","expected":"entry"}]',
  '2020-12-27 11:53:00'
), (
  'TCP有一个{A}(maximum segment size, 最大分节大小), 用于向对端TCP通告对端在每个分节中能发送的最大TCP数据量。',
  '[{"code":"A","expected":"MSS"}]',
  '2020-12-27 11:53:00'
), (
  'TCP套接字是一种{A}套接字(stream socket)。TCP关心确认、超时和重传之类的细节。',
  '[{"code":"A","expected":"流"}]',
  '2020-12-27 11:53:00'
), (
  '大多数套接字函数都需要一个指向套接字地址结构的指针作为参数。每个协议族都定义它自己的套接字地址结构。这些结构的名字均以{A}开头，并以对应每个协议族的{B}后缀结尾。',
  '[{"code":"A","expected":"sockaddr_"},{"code":"B","expected":"唯一"}]',
  '2020-12-27 11:53:00'
)
;

--drop table if exists t_completion_option;
--create table t_completion_option (
--  id int primary key,
--  completion_id int comment '所属填空题',
--  code varchar(20) comment '占位代码',
--  expected varchar(20) comment '答案'
--) engine=InnoDB default charset=utf8mb4;
--insert into t_completion_option(id, completion_id, code, expected) values(1, 1, 's', '/^apple$/i');
