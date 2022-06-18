package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.User;
import ru.job4j.todo.persistence.UserDbStore;

import java.util.Optional;

/**
 * Верхний слой хранилища UserDbStore в котором находятся пользователи.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
@Service
public class UserService {
    private final UserDbStore store;

    public UserService(UserDbStore store) {
        this.store = store;
    }

    /**
     * Добавление пользователя в базу данных.
     *
     * @param user Новый пользователь.
     */
    public Optional<User> create(User user) {
        return store.create(user);
    }

    /**
     * Поиск пользователя по id.
     *
     * @param id Id пользователя.
     * @return Найденный пользователь.
     */
    public User findById(int id) {
        return store.findById(id);
    }

    /**
     * Поиск пользователя по имени и паролю.
     *
     * @param name     Имя пользователя.
     * @param password Пароль пользователя.
     * @return Найденный пользователь.
     */
    public Optional<User> findUserByNameAndPwd(String name, String password) {
        return store.findUserByNameAndPwd(name, password);
    }
}