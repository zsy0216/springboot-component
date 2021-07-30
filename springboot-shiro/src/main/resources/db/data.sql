DELETE FROM sys_user;
INSERT INTO sys_user VALUES
(1, 'root', '123456', NULL),
(2, 'test', '123456', NULL);

DELETE FROM sys_role;
INSERT INTO sys_role VALUES
(1, 'root', '超级管理员'),
(2, 'admin', '管理员'),
(3, 'guest', '游客');

DELETE FROM sys_permission;
INSERT INTO sys_permission VALUES
(1, 'view:user', 'view'),
(2, 'add:user', 'add'),
(3, 'update:user', 'update');

DELETE FROM sys_user_role;
INSERT INTO sys_user_role VALUES
(1, 1),
(1, 2),
(2, 3);

DELETE FROM sys_role_permission;
INSERT INTO sys_role_permission VALUES
(1, 1),
(1, 2),
(1, 3),
(2, 1),
(2, 2),
(2, 3),
(3, 1);
