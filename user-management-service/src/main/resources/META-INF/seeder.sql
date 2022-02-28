INSERT INTO roles(id, name) VALUES(1, "Administrator")
INSERT INTO roles(id, name) VALUES(2, "Guest")

INSERT INTO users(id, email, protected_password, first_name, last_name) VALUES(1, 'root@gmail.com', '8d969eef6ecad3c29a3a629280e686cf0c3f5d5a86aff3ca12020c923adc6c92', 'Root', 'Administrator');

INSERT INTO users_roles(user_id, role_id) VALUES(1, 1)
