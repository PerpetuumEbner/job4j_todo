package ru.job4j.todo.service;

import net.jcip.annotations.ThreadSafe;
import org.springframework.stereotype.Service;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.persistence.ItemDbStore;

import java.util.List;

/**
 * Верхний слой хранилища ItemDbStore в котором находятся задания.
 *
 * @author yustas
 * @version 1.0
 */
@ThreadSafe
@Service
public class ItemService {
    private final ItemDbStore store;

    public ItemService(ItemDbStore store) {
        this.store = store;
    }

    /**
     * Добавление задания в базу данных.
     *
     * @param item Новое задание.
     */
    public void create(Item item) {
        store.create(item);
    }

    /**
     * Обновление параметров задания.
     *
     * @param item Обновленное задание.
     */
    public void update(Item item) {
        store.update(item);
    }

    /**
     * Удаление задания по id.
     *
     * @param id Id задания.
     */
    public void delete(int id) {
        store.delete(id);
    }

    /**
     * Выполнение задания.
     *
     * @param id Id выполненного задания.
     */
    public void complete(int id) {
        store.complete(id);
    }

    /**
     * Поиск всех заданий в базе данных.
     *
     * @return Список найденных заданий.
     */
    public List findAll() {
        return store.findAll();
    }

    /**
     * Поиск задания по Id.
     *
     * @param id Id кандидата.
     * @return Найденный кандидат.
     */
    public Item findById(int id) {
        return store.findById(id);
    }

    /**
     * Поиск выполненных заданий.
     *
     * @return Список выполненных заданий.
     */
    public List findByCompletedItems() {
        return store.findByCompletedItems();
    }

    /**
     * Поиск новых заданий.
     *
     * @return Список новых заданий.
     */
    public List findByActualItems() {
        return store.findByActualItems();
    }
}