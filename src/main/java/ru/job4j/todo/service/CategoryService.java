package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.persistence.CategoryDbStore;

import java.util.List;

/**
 * Верхний слой хранилища CategoryDbStore в котором находятся категории.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
@Service
public class CategoryService {

    private final CategoryDbStore store;

    public CategoryService(CategoryDbStore store) {
        this.store = store;
    }

    /**
     * Добавление категорий в базу данных.
     *
     * @param category Новая категория.
     */
    public void create(Category category) {
        store.create(category);
    }

    /**
     * Удаление категории по id.
     *
     * @param id Id категории.
     */
    public void delete(int id) {
        store.delete(id);
    }

    /**
     * Поиск категории по id.
     *
     * @param id Id категории.
     * @return Найденная категория.
     */
    public Category findByID(int id) {
        return store.findById(id);
    }

    /**
     * Поиск всех категорий в базе данных.
     *
     * @return Список найденных категорий.
     */
    public List findAll() {
        return store.findAll();
    }
}