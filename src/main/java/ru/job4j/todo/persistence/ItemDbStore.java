package ru.job4j.todo.persistence;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.function.Function;

/**
 * В классе происходит обработка заданий в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class ItemDbStore {
    private final SessionFactory sf;

    public ItemDbStore(SessionFactory sf) {
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
     * Добавление задания в базу данных.
     *
     * @param item Новое задание.
     */
    public void create(Item item) {
        this.tx(session -> session.save(item));
    }

    /**
     * Обновление параметров задания по id.
     *
     * @param item Обновлённое задание.
     */
    public void update(Item item) {
        this.tx(
                session -> session.createQuery("update Item set name = :newName, description = :newDescription, "
                                + "done = :newDone where id = :id")
                        .setParameter("id", item.getId())
                        .setParameter("newName", item.getName())
                        .setParameter("newDescription", item.getDescription())
                        .setParameter("newDone", item.isDone())
                        .executeUpdate()
        );
    }

    /**
     * Удаление задания по id.
     *
     * @param id Id задания.
     */
    public void delete(int id) {
        this.tx(
                session -> session.createQuery("delete from Item where id = :id")
                        .setParameter("id", id).executeUpdate()
        );
    }

    /**
     * Выполнение задания.
     *
     * @param id Id выполненного задания.
     */
    public void complete(int id) {
        this.tx(
                session -> session.createQuery("update Item set done = true where id = :id")
                        .setParameter("id", id).executeUpdate()
        );
    }

    /**
     * Поиск всех заданий в базе данных.
     *
     * @return Список найденных заданий.
     */
    public List findAll() {
        return this.tx(
                session -> session.createQuery("from Item ").list()
        );
    }

    /**
     * Поиск задания по Id.
     *
     * @param id Id кандидата.
     * @return Найденный кандидат.
     */
    public Item findById(int id) {
        return this.tx(
                session -> (Item) session.createQuery("from Item where id = :id")
                        .setParameter("id", id).uniqueResult()
        );
    }

    /**
     * Поиск выполненных заданий.
     *
     * @return Список выполненных заданий.
     */
    public List findByCompletedItems() {
        return this.tx(
                session -> session.createQuery("from Item where done = true").list()
        );
    }

    /**
     * Поиск новых заданий.
     *
     * @return Список новых заданий.
     */
    public List findByActualItems() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);
        Timestamp actualTime = Timestamp.valueOf(dateTime);
        return this.tx(
                session -> session
                        .createQuery("from Item where created > :actualTime ")
                        .setParameter("actualTime", actualTime).list()
        );
    }
}