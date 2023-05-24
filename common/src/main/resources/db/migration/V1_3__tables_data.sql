INSERT INTO public.c_roles
    (role_name)
VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER');

INSERT INTO public.users
    ("name", email, "password")
VALUES
    ('admin', 'admin@mail.ru', 'admin'),
    ('user1', 'user1@mail.com', 'user1'),
    ('user2', 'user2@mail.com', 'user2');

INSERT INTO public.users_roles
    (user_id, role_id)
VALUES
    (1, 1),
    (2, 2),
    (3, 2);


--INSERT INTO public.c_question_type
--    (question_type)
--VALUES
--    ('PLAIN'),
--    ('CHECKBOX'),
--    ('DROP_DOWN_LIST');