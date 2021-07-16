DELETE FROM user;

INSERT INTO user (id, name, age, email) VALUES
(1, 'Jone', 18, 'test1@zhangshuaiyin.com'),
(2, 'Jack', 20, 'test2@zhangshuaiyin.com'),
(3, 'Tom', 28, 'test3@zhangshuaiyin.com'),
(4, 'Sandy', 20, 'test4@zhangshuaiyin.com'),
(5, 'Billie', 24, 'test5@zhangshuaiyin.com');


INSERT INTO customer (id, name, age, email, create_time, update_time, deleted) VALUES
('1', 'Jone', 18, 'test1@zhangshuaiyin.com', '2021-07-15 15:37:56', '2021-07-15 15:37:56', 0),
('2', 'Jack', 20, 'test2@zhangshuaiyin.com', '2021-07-15 15:37:56', '2021-07-15 15:37:56', 0),
('3', 'Tom', 28, 'test3@zhangshuaiyin.com', '2021-07-15 15:37:56', '2021-07-15 15:37:56', 0),
('4', 'Sandy', 20, 'test4@zhangshuaiyin.com', '2021-07-15 15:37:56', '2021-07-15 15:37:56', 0),
('5', 'Billie', 24, 'test5@zhangshuaiyin.com', '2021-07-15 15:37:56', '2021-07-15 15:37:56', 0);
