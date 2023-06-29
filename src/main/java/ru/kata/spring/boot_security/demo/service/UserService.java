package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User getById(Long id);

    void save(User user);

    void update(User userUpdate);

    void delete(Long id);

    User findByUsername(String username);
}
