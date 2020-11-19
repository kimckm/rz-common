drop table if exists t_order;

create table t_order (
  id int primary key auto_increment,
  no varchar(20) comment '中台订单编号',
  base_price int comment '总底价',
  status int comment '处理状态。0=草稿 1=已提交 2=已确认 3=已配货 4=已发货 99=已取消',
  remark varchar(255) comment '备注',
  create_at datetime comment '创建时间'
) engine=InnoDB default charset=utf8mb4;

insert into t_order(no, base_price, status, remark, create_at) values('no1', 800, 0, '备注值', '2020-11-01 11:53:00');
