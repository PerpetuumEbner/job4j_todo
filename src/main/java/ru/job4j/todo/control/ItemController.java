package ru.job4j.todo.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Item;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;
import ru.job4j.todo.service.ItemService;

import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

@ThreadSafe
@Controller
public class ItemController {
    private final ItemService itemService;

    private final CategoryService categoryService;

    @Autowired
    public ItemController(ItemService store, CategoryService categoryService) {
        this.itemService = store;
        this.categoryService = categoryService;
    }

    @GetMapping("/items")
    public String items(Model model, HttpSession session) {
        userHttpSession(model, session);
        model.addAttribute("items", itemService.findAll());
        return "items";
    }

    @GetMapping("/formAddItem")
    public String addItem(Model model, HttpSession session) {
        userHttpSession(model, session);
        model.addAttribute("item", new Item());
        model.addAttribute("categories", categoryService.findAll());
        return "addItem";
    }

    @PostMapping("/createItem")
    public String createItem(@ModelAttribute Item item, @RequestParam("categoryId") List<Integer> categoriesId) {
        item.setCreated(Timestamp.from(Instant.now()));
        for (Integer id : categoriesId) {
            item.addCategory(categoryService.findByID(id));
        }
        itemService.create(item);
        return "redirect:/items";
    }

    @GetMapping("/items/{itemId}")
    public String item(Model model, @PathVariable("itemId") int id) {
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("category", categoryService.findByID(id));
        return "item";
    }

    @GetMapping("/formDeleteItem/{itemId}")
    public String formDeleteItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        userHttpSession(model, session);
        itemService.delete(id);
        return "redirect:/items";
    }

    @GetMapping("/formUpdateItem/{itemId}")
    public String formUpdateItem(Model model, @PathVariable("itemId") int id, HttpSession session) {
        userHttpSession(model, session);
        model.addAttribute("item", itemService.findById(id));
        model.addAttribute("categories", categoryService.findAll());
        return "updateItem";
    }

    @PostMapping("/updateItem")
    public String updateItem(@ModelAttribute Item item, @RequestParam("categoryId") List<Integer> categoriesId) {
        for (Integer id : categoriesId) {
            item.addCategory(categoryService.findByID(id));
        }
        itemService.update(item);
        return "redirect:/items";
    }

    @GetMapping("/completed/{itemId}")
    public String completed(Model model, @PathVariable("itemId") int id, HttpSession session) {
        userHttpSession(model, session);
        itemService.complete(id);
        return "redirect:/items";
    }

    @GetMapping("/completedItems")
    public String completedItems(Model model, HttpSession session) {
        userHttpSession(model, session);
        model.addAttribute("items", itemService.findByCompletedItems());
        return "items";
    }

    @GetMapping("/actualItems")
    public String actualItems(Model model, HttpSession session) {
        userHttpSession(model, session);
        model.addAttribute("items", itemService.findByActualItems());
        return "items";
    }

    private void userHttpSession(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("user", user);
    }
}