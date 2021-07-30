DROP TABLE IF EXISTS sys_user;

CREATE TABLE sys_user (
 id int(200) NOT NULL COMMENT '用户ID',
 username char(50) NOT NULL COMMENT '账号',
 password char(255) NOT NULL COMMENT '密码',
 salt varchar(255) DEFAULT NULL COMMENT '盐值',
 PRIMARY KEY (id)
);
