insert into person(`id`, `name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`, `job`) values (2, 'martin', 10, 'A', 1991, 8, 15, 'programmer');
insert into person(`id`, `name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (3, 'david', 9, 'B', 1992, 7, 21);
insert into person(`id`, `name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (4, 'dennis', 8, 'O', 1993, 10, 15);
insert into person(`id`, `name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (5, 'sophia', 7, 'AB', 1994, 8, 31);
insert into person(`id`, `name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values (6, 'benny', 6, 'A', 1995, 12, 23);

insert into block(`id`, `name`) values(2, 'dennis');
insert into block(`id`, `name`) values(3, 'sophia');

update person set block_id = 2 where id = 4;
update person set block_id = 3 where id = 5;
