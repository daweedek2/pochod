-- user
INSERT INTO pop_user(id, username, password)
SELECT 1, 'admin', '$2a$12$gzQQy9oVY1v20qWzIcfWP.fp0FSWOV01c7tM5gfdwR9v8c6QoAyEW'
    WHERE NOT EXISTS(
    SELECT * FROM pop_user WHERE id = 1);
INSERT INTO pop_user(id, username, password)
SELECT 2, 'organizator', '$2a$12$k/oFmX6VBM/QBs1IM6JLTOllN2nGhcVErMsKvene7lHhT17Gds.H2'
    WHERE NOT EXISTS(
    SELECT * FROM pop_user WHERE id = 2);

-- roles
INSERT INTO pop_role(id, role)
SELECT 1, 'ADMIN'
    WHERE NOT EXISTS(
    SELECT * FROM pop_role WHERE id = 1);
INSERT INTO pop_role(id, role)
SELECT 2, 'ORGANIZATOR'
    WHERE NOT EXISTS(
    SELECT * FROM pop_role WHERE id = 2);
INSERT INTO pop_role(id, role)
SELECT 3, 'USER'
    WHERE NOT EXISTS(
    SELECT * FROM pop_role WHERE id = 3);

-- user-role mapping
INSERT INTO user_role(user_id, role_id)
SELECT 1, 1
    WHERE NOT EXISTS(
    SELECT * FROM user_role WHERE user_id = 1 AND role_id = 1);
INSERT INTO user_role(user_id, role_id)
SELECT 2, 2
    WHERE NOT EXISTS(
    SELECT * FROM user_role WHERE user_id = 2 AND role_id = 2);
