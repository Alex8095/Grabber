INSERT INTO role (role) VALUES ('admin'), ('moderator');
INSERT INTO user (login, password) VALUES ('moder', '111111'), ('alex', '843887');
INSERT INTO user_role (user_id, role_id) VALUES (1, 2), (2, 1);