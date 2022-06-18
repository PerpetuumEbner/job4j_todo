package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.User;

import java.util.Optional;
import java.util.function.Function;

/**
 * В классе происходит обработка пользователей в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class UserDbStore {
    private final SessionFactory sf;

    public UserDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    private <T> T tx(final Function<Session, T> command) {
        final Session session = sf.openSession();
        final Transaction tx = session.beginTransaction();
        try {
            T rsl = command.apply(session);
            tx.commit();
            return rsl;
        } catch (final Exception e) {
            session.getTransaction().rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * Добавление пользователя в базу данных.
     *
     * @param user Новый пользователь.
     */
    public Optional<User> create(User user) {
        Optional result = Optional.empty();
        try {
            result = Optional.of(this.tx(session -> session.save(user)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Поиск пользователя по id.
     *
     * @param id Id пользователя.
     * @return Найденный пользователь.
     */
    public User findById(int id) {
        return this.tx(session -> (User) session.createQuery(
                "from User where id = :id").setParameter("id", id).uniqueResult());
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
                .uniqueResultOptional()
        );
    }
}