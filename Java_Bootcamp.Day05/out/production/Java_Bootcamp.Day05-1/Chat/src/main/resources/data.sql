--TABLE USER--
truncate table Chat.User cascade;
insert into Chat.User (login, password) values ('login1', 'pss');
insert into Chat.User (login, password) values ('login2', 'pss');
insert into Chat.User (login, password) values ('login3', 'pss');
insert into Chat.User (login, password) values ('login4', 'pss');
insert into Chat.User (login, password) values ('login5', 'pss');

--TABLE CHATROOM--
truncate table Chat.Chatroom cascade;
insert into Chat.Chatroom(chatroom_name, chatroom_owner)
values ('room1', (select user_id from Chat.User where login = 'login1'));
insert into Chat.Chatroom(chatroom_name, chatroom_owner)
values ('room2', (select user_id from Chat.User where login = 'login2'));
insert into Chat.Chatroom(chatroom_name, chatroom_owner)
values ('room3', (select user_id from Chat.User where login = 'login3'));
insert into Chat.Chatroom(chatroom_name, chatroom_owner)
values ('room4', (select user_id from Chat.User where login = 'login4'));

-- TABLE MESSAGE --
truncate table Chat.Message cascade;
insert into Chat.Message (message_author, message_room, message_text) values
((select user_id from Chat.User where login = 'login1'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2'), 'Привет)'),
((select user_id from Chat.User where login = 'login2'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2'), 'Да, привет!'),
 ((select user_id from Chat.User where login = 'login1'),
  (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2'), 'Как дела?'),
((select user_id from Chat.User where login = 'login2'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2'), 'Да все хорошо, сам как?'),
((select user_id from Chat.User where login = 'login1'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2'), 'Норм. Передай пожалуйста, login4, что я не приду.'),
((select user_id from Chat.User where login = 'login2'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2'), 'OK!'),
((select user_id from Chat.User where login = 'login2'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room3'), 'Здарова! login1 не придет сказал');

-- TABLE USERCHATROOMS --
truncate Chat.UserChatRooms cascade;
insert into Chat.UserChatRooms (user_id, chatroom_id) values
((select user_id from Chat.User where login = 'login1'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2')),
((select user_id from Chat.User where login = 'login2'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2')),
((select user_id from Chat.User where login = 'login3'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room3')),
((select user_id from Chat.User where login = 'login4'),
 (select chatroom_id from Chat.Chatroom where chatroom_name = 'room2'));
