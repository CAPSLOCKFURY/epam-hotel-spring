insert into room_class(id) values (1), (2), (3), (4);

insert into room_class_translation(roomclass_id, name, language)
values (1, 'Cheap', 'en'), (1, 'Дешевый', 'ru'),
       (2, 'Normal', 'en'), (2, 'Обычный', 'ru'),
       (3, 'Half lux', 'en'), (3, 'Полу люкс', 'ru'),
       (4, 'Lux', 'en'), (4, 'Люкс', 'ru');

insert into rooms (number, status, name, price, capacity, roomclass_id)
values (1, default, 'Room 1', 100, 2, 1), (2, default, 'Room 2', 110, 3, 1), (3, default, 'Room 3', 120, 3, 1), (4, default, 'Room 4', 150, 4, 1),
       (5, default, 'Normal Room 1', 200, 4, 2),(6, default, 'Normal Room 2', 220, 4, 2), (7, default, 'Normal Room 3', 250, 4, 2), (8, default, 'Normal Room 4', 300, 6, 2),
       (9, default, 'Half lux Room 1', 350, 4, 3),(10, default, 'Half lux Room 2', 370, 5, 3), (11, default, 'Half lux Room 3', 400, 4, 3), (12, default, 'Half lux Room 4', 500, 6, 3),
       (13, default, 'lux Room 1', 700, 8, 4),(14, default, 'lux Room 2', 1000, 8, 4);

insert into room_registry(archived, check_in_date, check_out_date, room_id, user_id)
values (false, '25-03-2022', '30-03-2022', 2, 1);

update rooms set status = 'FREE';