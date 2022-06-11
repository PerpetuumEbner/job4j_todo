package ru.job4j.todo.persistence;

import org.hibernate.Session;
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
public class ItemDbStore {
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
        Session session = sf.openSession();
        session.beginTransaction();
        session.save(item);
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Обновление параметров задания по id.
     *
     * @param item
     */
    public void update(Item item) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update Item set name = :newName, description = :newDescription, created = :newCreated, "
                        + "done = :newDone where id = :id")
                .setParameter("id", item.getId())
                .setParameter("newName", item.getName())
                .setParameter("newDescription", item.getDescription())
                .setParameter("newCreated", item.getCreated())
                .setParameter("newDone", item.isDone())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Удаление задания по id.
     *
     * @param id Id задания.
     */
    public void delete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("delete from Item where id = :id")
                .setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Выполнение задания.
     *
     * @param id Id выполненного задания.
     */
    public void complete(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        session.createQuery("update Item set done = true where id = :id")
                .setParameter("id", id).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }

    /**
     * Поиск всех заданий в базе данных.
     *
     * @return Список найденных заданий.
     */
    public List findAll() {
        Session session = sf.openSession();
        session.beginTransaction();
        var result = session.createQuery("from Item ").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Поиск задания по Id.
     *
     * @param id Id кандидата.
     * @return Найденный кандидат.
     */
    public Item findById(int id) {
        Session session = sf.openSession();
        session.beginTransaction();
        Item result = (Item) session.createQuery("from Item where id = :id")
                .setParameter("id", id).uniqueResult();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Поиск выполненных заданий.
     *
     * @return Список выполненных заданий.
     */
    public List findByCompletedItems() {
        Session session = sf.openSession();
        session.beginTransaction();
        var result = session.createQuery("from Item where done = true").list();
        session.getTransaction().commit();
        session.close();
        return result;
    }

    /**
     * Поиск новых заданий.
     *
     * @return Список новых заданий.
     */
    public List findByActualItems() {
        LocalDateTime dateTime = LocalDateTime.now().minusDays(1);
        Timestamp actualTime = Timestamp.valueOf(dateTime);
        Session session = sf.openSession();
        session.beginTransaction();
        var result = session
                .createQuery("from Item where created > :actualTime ")
                .setParameter("actualTime", actualTime).list();
        session.getTransaction().commit();
        session.close();
        return result;
    }
}