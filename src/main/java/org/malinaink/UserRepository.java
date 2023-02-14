package org.malinaink;

import java.util.*;

public class UserRepository {

    private List<User> users = new ArrayList<>();

    public Collection<User> getAllUsers() {
        return Collections.unmodifiableCollection(users);
    }

    public Optional<User> findByLogin(String login) {
        return this.getAllUsers()
                .stream()
                .filter(u -> u.getLogin().equalsIgnoreCase(login))
                .findFirst();
    }

    public Optional<User> findByLoginAndPassword(String login, String password) {
        return this.getAllUsers().stream()
                .filter(u -> u.getLogin() != null && u.getLogin().equalsIgnoreCase(login))
                .filter(u -> u.getPassword() != null && u.getPassword().equals(password))
                .findFirst();
    }

    public User addUser(User user) {
        this.users.add(user);
        return user;
    }
}


