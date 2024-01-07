INSERT INTO permissions (id, name)
VALUES (4729618240557460389, 'create:email')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (8275556200748891291, 'read:email')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (5902745182854687593, 'search:email')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (2015496723840437024, 'update:email')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (994683017615056126, 'delete:email')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (3857011580473275038, 'create:message')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (1338775237294518946, 'read:message')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (1360356789922831879, 'search:message')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (1623244612615767845, 'update:message')
ON CONFLICT (id) DO NOTHING;
INSERT INTO permissions (id, name)
VALUES (1053987496325183017, 'delete:message')
ON CONFLICT (id) DO NOTHING;


INSERT INTO roles (id, name)
VALUES (1018360929362981941, 'ADMIN')
ON CONFLICT (id) DO NOTHING;
INSERT INTO roles (id, name)
VALUES (1655746481291160804, 'USER')
ON CONFLICT (id) DO NOTHING;
INSERT INTO roles (id, name)
VALUES (1937076775882171205, 'SUPER_ADMIN')
ON CONFLICT (id) DO NOTHING;


INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 4729618240557460389)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 8275556200748891291)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 5902745182854687593)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 2015496723840437024)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 994683017615056126)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 3857011580473275038)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 1338775237294518946)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 1360356789922831879)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 1623244612615767845)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1018360929362981941, 1053987496325183017)
ON CONFLICT (role_id, permissions_id) DO NOTHING;

INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 4729618240557460389)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 8275556200748891291)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 5902745182854687593)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 2015496723840437024)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 994683017615056126)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 3857011580473275038)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 1338775237294518946)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 1360356789922831879)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 1623244612615767845)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1655746481291160804, 1053987496325183017)
ON CONFLICT (role_id, permissions_id) DO NOTHING;

INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 4729618240557460389)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 8275556200748891291)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 5902745182854687593)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 2015496723840437024)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 994683017615056126)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 3857011580473275038)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 1338775237294518946)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 1360356789922831879)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 1623244612615767845)
ON CONFLICT (role_id, permissions_id) DO NOTHING;
INSERT INTO roles_permissions (role_id, permissions_id)
VALUES (1937076775882171205, 1053987496325183017)
ON CONFLICT (role_id, permissions_id) DO NOTHING;


INSERT INTO users(id, email, password, is_verified)
VALUES (4078938095660883582, 'khanhk56a123@gmail.com', '$2a$10$5VE4AkdL42to0pWeh2ZS0eyUs90xJKyfKy3SDJ.jTcxxNSptd3Lci', true)
ON CONFLICT (id) DO NOTHING;

INSERT INTO users_roles(user_id, role_id)
VALUES (4078938095660883582, 1937076775882171205)
ON CONFLICT (user_id, role_id) DO NOTHING;