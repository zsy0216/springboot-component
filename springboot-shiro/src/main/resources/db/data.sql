DELETE FROM sys_user;
INSERT INTO sys_user VALUES
(1, 'root', 'ac9e5a212f96f614bd2f07e292a78bc4f144dd3e', '97b189201c85cd7994fbb5f04814f110'),
(2, 'test', '123456', NULL);

DELETE FROM sys_role;
INSERT INTO sys_role VALUES
(1, 'root', '超级管理员'),
(2, 'admin', '管理员'),
(3, 'guest', '游客');

DELETE FROM sys_permission;
INSERT INTO sys_permission VALUES
(1, 'user:add', 'add'),
(2, 'user:update', 'update'),
(3, 'system:view', 'view');

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
