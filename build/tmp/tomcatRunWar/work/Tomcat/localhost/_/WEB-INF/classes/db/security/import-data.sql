INSERT INTO role (role) VALUES ('admin'), ('moderator');
INSERT INTO user (login, password) VALUES ('moder', '111111'), ('alex', '843887');
INSERT INTO user_role (user_id, role_id) VALUES (1, 2), (2, 1);

//dictionary
insert into dictionary (id, code, description, name) values (1, "dictionary code", "dictionary description", "dictionary  name");
insert into dictionary_value (1, code, description, name, dictionary_id) values (1, "dv code 1", "dv description 1", "dv  name 1");
insert into dictionary_value (2, code, description, name, dictionary_id) values (2, "dv code 2", "dv description 2", "dv  name 2");
