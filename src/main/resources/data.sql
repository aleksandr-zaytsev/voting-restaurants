INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 3);

insert into RESTAURANT (NAME)
values ('Italian');
insert into RESTAURANT (NAME)
values ('Chinese');
insert into RESTAURANT (NAME)
values ('Russian');

insert into MENU (RESTAURANT_ID, MENU_DATE)
values (1, NOW());
insert into MENU (RESTAURANT_ID, MENU_DATE)
values (2, NOW());
insert into MENU (RESTAURANT_ID, MENU_DATE)
values (3, NOW());

insert into DISH (NAME, PRICE, MENU_ID)
values ('Pasta', 500, 1);
insert into DISH (NAME, PRICE, MENU_ID)
values ('Pizza',700 , 1);
insert into DISH (NAME, PRICE, MENU_ID)
values ('Risotto', 900, 1);
insert into DISH (NAME, PRICE, MENU_ID)
values ('Noodles', 300, 2);
insert into DISH (NAME, PRICE, MENU_ID)
values ('Peking duck', 1100, 2);
insert into DISH (NAME, PRICE, MENU_ID)
values ('Rice', 200, 2);
insert into DISH (NAME, PRICE, MENU_ID)
values ('Potato with meat', 800, 3);
insert into DISH (NAME, PRICE, MENU_ID)
values ('Soup', 300, 3);
insert into DISH (NAME, PRICE, MENU_ID)
values ('Eggs', 400, 3);

insert into VOTE (USER_ID, RESTAURANT_ID, VOTE_DATE)
values (1, 1, NOW());

