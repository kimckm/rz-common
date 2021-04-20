CREATE TABLE `t_choice` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `question` varchar(255) NOT NULL COMMENT '题目',
  `created_at` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选择题';

CREATE TABLE `t_choice_option` (
  `id` int PRIMARY KEY AUTO_INCREMENT,
  `option` varchar(255) NOT NULL COMMENT '选项值',
  `correct` tinyint NOT NULL COMMENT '选项是否正确',
  `seq` tinyint NOT NULL COMMENT '顺序',
  `choice_id` int NOT NULL COMMENT '选择题ID',
  KEY `fk_choice_id` (`choice_id`),
  CONSTRAINT `fk_choice_id` FOREIGN KEY (`choice_id`) REFERENCES `t_choice` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='选择题可选项';
