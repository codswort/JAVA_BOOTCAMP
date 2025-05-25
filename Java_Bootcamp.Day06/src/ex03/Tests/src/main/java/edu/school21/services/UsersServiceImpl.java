package edu.school21.services;

import edu.school21.exceptions.AlreadyAuthenticatedException;
import edu.school21.exceptions.EntityNotFoundException;
import edu.school21.models.User;
import edu.school21.repositories.UsersRepository;

public class UsersServiceImpl {
    private UsersRepository usersRepository;
    public UsersServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public boolean authenticate(String login, String password) {
        User user = usersRepository.findByLogin(login);
        if (user == null) {
            throw new EntityNotFoundException("User not found!");
        }
        if (user.getStatus()) {
            throw new AlreadyAuthenticatedException("Already user is authenticated!");
        }
        if (password.equals(user.getPassword())) {
            user.setStatus(true);
            usersRepository.update(user);
            return true;
        }
        return false;
    }
}