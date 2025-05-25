package edu.school21.repositories;

import edu.school21.models.User;

public interface UsersRepository {
    User findByLogin(String login);
    void update(User user);
}

//Предполагается, что метод findByLogin возвращает объект пользователя,
//        найденный через логин, или выдает исключение EntityNotFoundException,
//        если пользователь с указанным логином не найден.
//        Метод Update выдает аналогичное исключение при обновлении пользователя, которого нет в базе данных