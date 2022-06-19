package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;

/**
 * В классе происходит обработка пользователей в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class UserDbStore implements Wrapper {
    private final SessionFactory sf;

    public UserDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    /**
     * Добавление пользователя в базу данных.
     *
     * @param user Новый пользователь.
     */
    public Optional<User> create(User user) {
        try {
            this.tx(session -> session.save(user), sf);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(user);
    }

    /**
     * Поиск пользователя по id.
     *
     * @param id Id пользователя.
     * @return Найденный пользователь.
     */
    public User findById(int id) {
        return this.tx(session -> (User) session.createQuery(
                "from User where id = :id").setParameter("id", id).uniqueResult(), sf);
    }

    /**
     * Поиск пользователя по имени и паролю.
     *
     * @param name     Имя пользователя.
     * @param password Пароль пользователя.
     * @return Найденный пользователь.
     */
    public Optional<User> findUserByNameAndPwd(String name, String password) {
        return this.tx(session -> session.createQuery(
                        "from User where name = :name and password = :password")
                .setParameter("name", name)
                .setParameter("password", password)
                .uniqueResultOptional(), sf
        );
    }
}