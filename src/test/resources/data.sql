insert into person(`name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`, `job`) values ('martin', 'A', 1991, 8, 15, 'programmer');
insert into person(`name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values ('david', 'B', 1992, 7, 21);
insert into person(`name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values ('dennis', 'O', 1993, 10, 15);
insert into person(`name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values ('sophia', 'AB', 1994, 8, 31);
insert into person(`name`, `blood_type`, `year_of_birthday`, `month_of_birthday`, `day_of_birthday`) values ('benny', 'A', 1995, 12, 23);

insert into block(`name`) values('dennis');
insert into block(`name`) values('sophia');

update person set block_id = 1 where id = 3;
update person set block_id = 2 where id = 4;
