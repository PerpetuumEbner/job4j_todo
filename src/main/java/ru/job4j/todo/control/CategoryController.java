package ru.job4j.todo.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.todo.model.Category;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.CategoryService;

import javax.servlet.http.HttpSession;

@ThreadSafe
@Controller
public class CategoryController {
    private final CategoryService store;

    @Autowired
    public CategoryController(CategoryService store) {
        this.store = store;
    }

    @GetMapping("/categories")
    public String categories(Model model, HttpSession session) {
        userHttpSession(model, session);
        model.addAttribute("categories", store.findAll());
        return "/categories";
    }

    @GetMapping("/formAddCategories")
    public String addCategories(Model model, HttpSession session) {
        userHttpSession(model, session);
        model.addAttribute("category", new Category());
        return "/addCategories";
    }

    @PostMapping("/createCategory")
    public String createCategory(@ModelAttribute Category category) {
        store.create(category);
        return "redirect:/categories";
    }

    @GetMapping("/formDeleteCategory/{categoryId}")
    public String deleteCategory(Model model, @PathVariable("categoryId") int id, HttpSession session) {
        userHttpSession(model, session);
        store.delete(id);
        return "redirect:/categories";
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