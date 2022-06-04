package ru.job4j.todo.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.service.ItemService;

import java.sql.Timestamp;
import java.time.Instant;

@ThreadSafe
@Controller
public class ItemController {
    private final ItemService store;

    @Autowired
    public ItemController(ItemService store) {
        this.store = store;
    }

    @GetMapping("/items")
    public String items(Model model) {
        model.addAttribute("items", store.findAll());
        return "items";
    }

    @GetMapping("/formAddItem")
    public String addItem(Model model) {
        model.addAttribute("item", new Item());
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item) {
        item.setCreated(Timestamp.from(Instant.now()));
        store.create(item);
        return "redirect:/items";
    }

    @GetMapping("/items/{itemId}")
    public String item(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", store.findById(id));
        return "item";
    }

    @GetMapping("/formDeleteItem/{itemId}")
    public String formDeleteItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", store.findById(id));
        return "deleteItem";
    }

    @PostMapping("deleteItem")
    public String deleteItem(@ModelAttribute Item item) {
        store.delete(item.getId());
        return "redirect:/items";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateItem(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", store.findById(id));
        return "updateItem";
    }

    @PostMapping("/updateItems")
    public String updateItem(@ModelAttribute Item item) {
        store.update(item.getId(), item.getName(), item.getDescription(), Timestamp.from(Instant.now()), item.isDone());
        return "redirect:/items";
    }

    @PostMapping("/completed")
    public String completed(@ModelAttribute Item item) {
        item.setDone(true);
        store.update(item.getId(), item.getName(), item.getDescription(), Timestamp.from(Instant.now()), item.isDone());
        return "items";
    }

    @GetMapping("/completedItems")
    public String completedItems(Model model) {
        model.addAttribute("completedItems", store.findByCompletedItems());
        return "completedItems";
    }

    @GetMapping("/actualItems")
    public String actualItems(Model model) {
        model.addAttribute("actualItems", store.findByActualItems());
        return "actualItems";
    }
}