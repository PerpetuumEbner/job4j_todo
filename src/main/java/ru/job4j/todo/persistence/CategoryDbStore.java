package ru.job4j.todo.persistence;

import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;
import ru.job4j.todo.model.Category;

import java.util.List;

/**
 * В классе происходит обработка категорий в базе данных.
 *
 * @author yustas
 * @version 1.0
 */
@Repository
public class CategoryDbStore implements Wrapper {
    private final SessionFactory sf;

    public CategoryDbStore(SessionFactory sf) {
        this.sf = sf;
    }

    /**
     * Добавление категорий в базу данных.
     *
     * @param category Новая категория.
     */
    public void create(Category category) {
        this.tx(session -> session.save(category), sf);
    }

    /**
     * Удаление категории по id.
     *
     * @param id Id категории.
     */
    public void delete(int id) {
        this.tx(session -> session.createQuery("delete from Category where id = :id")
                        .setParameter("id", id).executeUpdate(), sf);
    }

    /**
     * Поиск категории по id.
     *
     * @param id Id категории.
     * @return Найденная категория.
     */
    public Category findById(int id) {
        return this.tx(session -> (Category) session.createQuery("from Category where id = :id")
                        .setParameter("id", id).uniqueResult(), sf);
    }

    /**
     * Поиск всех категорий в базе данных.
     *
     * @return Список найденных категорий.
     */
    public List findAll() {
        return this.tx(
                session -> session.createQuery("from Category").list(), sf
        );
    }
}