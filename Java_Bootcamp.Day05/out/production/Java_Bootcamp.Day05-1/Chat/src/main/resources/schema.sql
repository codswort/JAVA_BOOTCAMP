drop schema if exists Chat cascade;
create schema if not exists Chat;

--TABLE USER--
create table if not exists Chat.User (
    user_id serial primary key,
    login varchar(50) not null unique,
    password varchar(50) not null
);

--TABLE CHATROOM--
create table if not exists Chat.Chatroom (
    chatroom_id serial primary key,
    chatroom_name varchar(50) not null unique,
    chatroom_owner int not null,
    foreign key (chatroom_owner) references Chat.User (user_id)
);

-- TABLE MESSAGE --
create table if not exists Chat.Message (
    message_id serial primary key,
    message_author int not null,
    message_room int not null,
    message_text text not null,
    message_date timestamp default CURRENT_TIMESTAMP,
    foreign key (message_author) references Chat.User(user_id),
    foreign key (message_room) references  Chat.Chatroom(chatroom_id)
);

-- TABLE USERCHATROOMS --
create table Chat.UserChatRooms (
    id serial primary key,
    user_id int not null,
    chatroom_id int not null,
    foreign key (user_id) references Chat.User(user_id),
    foreign key (chatroom_id) references Chat.Chatroom(chatroom_id)
);
