package src.main.java.edu.school21.Chat.repositories;

public enum Query {
    FIND_MESSAGE("SELECT * FROM Chat.Message m " +
            "JOIN Chat.User u ON m.message_author = u.user_id " +
            "JOIN Chat.Chatroom r ON m.message_room = r.chatroom_id " +
            "WHERE m.message_id = ?"),
    FIND_USER("SELECT * FROM Chat.User WHERE user_id = ?"),
    FIND_ROOM("SELECT * FROM Chat.Chatroom WHERE chatroom_id = ?"),
    SAVE_MESSAGE("insert into Chat.Message (message_author, message_room, message_text, message_date) " +
            "values (?, ?, ?, ?) returning message_id"),
    UPDATE("update Chat.Message SET message_text = ?, message_date = ? WHERE message_id = ?"),
    FIND_ALL("with PaginatedUsers as (\n" +
            "                select *\n" +
            "                from Chat.User\n" +
            "                order by user_id\n" +
            "                limit ?\n" +
            "                offset ?\n" +
            "            ),\n" +
            "UserCreatedChatRooms as (\n" +
            "                select user_id, chatroom_id, chatroom_name\n" +
            "                from Chat.Chatroom\n" +
            "                right join Chat.User on user_id = Chat.Chatroom.chatroom_owner\n" +
            "            ),\n" +
            "UserParticipation AS (\n" +
            "            select Chat.User.user_id, Chat.Chatroom.chatroom_id, chatroom_name\n" +
            "            from Chat.User\n" +
            "            left join Chat.UserChatRooms on Chat.User.user_id = Chat.UserChatRooms.user_id\n" +
            "            left join Chat.Chatroom on Chat.Chatroom.chatroom_id = Chat.UserChatRooms.chatroom_id\n" +
            "            )\n" +
            "select u.user_id, login, password,\n" +
            "       ARRAY_AGG(DISTINCT c.chatroom_id) created_room_id,\n" +
            "       ARRAY_AGG(DISTINCT c.chatroom_id) participated_room_id\n" +
            "from PaginatedUsers u\n" +
            "left join UserCreatedChatRooms c on u.user_id = c.user_id\n" +
            "left join UserParticipation p on u.user_id = p.user_id\n" +
            "group by 1,2,3\n" +
            "order by 1,2");

    private final String QUERY;

    Query(String QUERY) {
        this.QUERY = QUERY;
    }

    public String getQuery() {
        return QUERY;
    }
}

