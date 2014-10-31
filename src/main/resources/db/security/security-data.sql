INSERT INTO roles (role) VALUES ('admin'), ('moderator');
INSERT INTO users (login, password) VALUES ('moder', '111111'), ('alex', '843887');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2), (2, 1);
