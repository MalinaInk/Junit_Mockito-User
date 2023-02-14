package org.malinaink;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean createUser(String login, String password) {
        if (login == null || login.isBlank() || password == null || password.isBlank()) {
            throw new IllegalArgumentException("User should be defined");
        }
        boolean userExist = this.userRepository
                .getAllUsers()
                .stream()
                .anyMatch(t -> t.getLogin().equals(login));
        if (userExist) {
            throw new UserNonUniqueException("User already exist");
        }
        User user = new User(login, password);
        this.userRepository.addUser(user);
        return false;
    }


    public List<String> getLoginsOfAllUsers() {
        try {
            return this.userRepository
                    .getAllUsers()
                    .stream()
                    .map(User::getLogin)
                    .collect(Collectors.toList());
        } catch (RuntimeException e) {
            e.printStackTrace();
        }
        return null;
    }
}