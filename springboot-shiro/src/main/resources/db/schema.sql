DROP TABLE IF EXISTS sys_user;
CREATE TABLE sys_user (
 user_id int(11) NOT NULL COMMENT '用户ID',
 username char(50) NOT NULL COMMENT '账号',
 password char(255) NOT NULL COMMENT '密码',
 salt varchar(255) DEFAULT NULL COMMENT '盐值',
 PRIMARY KEY (user_id)
);

DROP TABLE IF EXISTS sys_role;
CREATE TABLE sys_role  (
role_id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统角色ID',
role_name char(255) NOT NULL COMMENT '系统角色名称',
description varchar(255) NOT NULL COMMENT '系统角色描述',
PRIMARY KEY (role_id)
);

DROP TABLE IF EXISTS sys_permission;
CREATE TABLE sys_permission  (
permission_id int(11) NOT NULL AUTO_INCREMENT COMMENT '系统权限ID',
permission_name char(255) NOT NULL COMMENT '系统权限名称',
description varchar(255) NOT NULL COMMENT '系统权限描述',
PRIMARY KEY (permission_id)
);

DROP TABLE IF EXISTS sys_user_role;
CREATE TABLE sys_user_role (
user_id int(11) NOT NULL COMMENT '用户ID',
role_id int(11) NOT NULL COMMENT '角色ID'
);

DROP TABLE IF EXISTS sys_role_permission;
CREATE TABLE sys_role_permission (
role_id int(11) NOT NULL COMMENT '角色ID',
permission_id int(11) NOT NULL COMMENT '权限ID'
)
