package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Item;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * В классе происходит обработка заданий в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class ItemDbStore implements Wrapper {
    private final SessionFactory sf;

    public ItemDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    /**
     * Добавление задания в базу данных.
     *
     * @param item Новое задание.
     */
    public void create(Item item) {
        this.tx(session -> session.save(item), sf);
    }

    /**
     * Обновление параметров задания по id.
     *
     * @param item Обновлённое задание.
     */
    public Item update(Item item) {
        return this.tx(
                session -> {
                    session.update(item);
                    return item;
                }, sf
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
                        .setParameter("id", id).executeUpdate(), sf
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
                        .setParameter("id", id).executeUpdate(), sf
        );
    }

    /**
     * Поиск всех заданий в базе данных.
     *
     * @return Список найденных заданий.
     */
    public List findAll() {
        return this.tx(
                session -> session.createQuery("select distinct i from Item i join fetch i.categories").list(), sf
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
                session -> (Item) session.createQuery("select distinct i from Item i join fetch i.categories where i.id = :id")
                        .setParameter("id", id).uniqueResult(), sf
        );
    }

    /**
     * Поиск выполненных заданий.
     *
     * @return Список выполненных заданий.
     */
    public List findByCompletedItems() {
        return this.tx(
                session -> session.createQuery("select distinct i from Item i join fetch i.categories where i.done = true").list(), sf
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
                        .createQuery("select distinct i from Item i join fetch i.categories where i.created > :actualTime ")
                        .setParameter("actualTime", actualTime).list(), sf
        );
    }
}