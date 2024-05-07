INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', '{noop}password'),
       ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('Guest', 'guest@gmail.com', '{noop}guest');

INSERT INTO USER_ROLE (role, user_id)
VALUES ('USER', 1),
       ('ADMIN', 2),
       ('USER', 2);

insert into DISH (NAME)
values ('Fish');
insert into DISH (NAME)
values ('Meat');
insert into DISH (NAME)
values ('Soup');
insert into DISH (NAME)
values ('Eggs');
insert into DISH (NAME)
values ('Coffee');
insert into DISH (NAME)
values ('Tea');


insert into RESTAURANT (NAME)
values ('Italian');
insert into RESTAURANT (NAME)
values ('Chinese');
insert into RESTAURANT (NAME)
values ('Russian');

insert into MENU (RESTAURANT_ID, DAY_OF_WEEK, DATE_MODIFY)
values (1, 'TUESDAY', CURRENT_TIMESTAMP());
insert into MENU (RESTAURANT_ID, DAY_OF_WEEK, DATE_MODIFY)
values (2, 'TUESDAY', CURRENT_TIMESTAMP());
insert into MENU (RESTAURANT_ID, DAY_OF_WEEK, DATE_MODIFY)
values (3, 'TUESDAY', CURRENT_TIMESTAMP());

insert into VOTE (RESTAURANT_ID, USER_ID, DATE)
values (1, 1, '2024-05-07');
insert into VOTE (RESTAURANT_ID, USER_ID, DATE)
values (2, 1, '2024-05-07');
insert into VOTE (RESTAURANT_ID, USER_ID, DATE)
values (3, 1, '2024-05-07');


insert into MENU_ITEM (DISH_ID, MENU_ID, PRICE)
values (1, 1, 500);
insert into MENU_ITEM (DISH_ID, MENU_ID, PRICE)
values (2, 1, 400);
insert into MENU_ITEM (DISH_ID, MENU_ID, PRICE)
values (3, 1, 300);
insert into MENU_ITEM (DISH_ID, MENU_ID, PRICE)
values (4, 2, 200);
insert into MENU_ITEM (DISH_ID, MENU_ID, PRICE)
values (5, 3, 100);
