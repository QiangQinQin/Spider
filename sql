
数据库spider

实习僧网站对应的数据库：
sxs
create table sxs (
id              bigint(20)      not null auto_increment    comment 'id，自增无意义',
name            varchar(40)     not null                   comment '公司名称',
job_url         varchar(100)    not null                   comment '网申URL',
work_city       varchar(50)     not null                   comment '工作城市',
price           varchar(50)     not null                   comment '岗位薪资',
job_name        varchar(100)    not null                   comment '工作岗位',
company_info    varchar(100)                               comment '公司简介',
create_time     datetime        default current_timestamp  comment '录入时间',
state           int             default 0                  comment '消息状态 0:未发送  1:已群推',
md5_key         varchar(100)     default ''                 comment 'key值',
primary key (id)
) engine=innodb comment = '实习僧校招信息';


存储要发送给的用户信息：
user
CREATE TABLE `user` (
`id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id，自增无意义',
`name` varchar(40) NOT NULL COMMENT '学生姓名',
`email` varchar(100) NOT NULL COMMENT '学生邮箱',
`graduate_time` int(11) NOT NULL COMMENT '毕业年份',
`school` varchar(50)  COMMENT '学校',
`class` varchar(50)  COMMENT '图论班级',
`create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '录入时间',
PRIMARY KEY (`id`),
UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8 COMMENT='学生信息表'    
