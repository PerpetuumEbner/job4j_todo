package ru.job4j.todo.control;

import net.jcip.annotations.ThreadSafe;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

@ThreadSafe
@Controller
public class UserController {
    private final UserService store;

    @Autowired
    public UserController(UserService service) {
        this.store = service;
    }

    @GetMapping("/formAddUser")
    public String addUser(Model model, @RequestParam(name = "fail", required = false) Boolean fail) {
        model.addAttribute("fail", fail != null);
        model.addAttribute("user",
                new User(0, "Введите имя",
                        "Введите пароль"));
        return "addUser";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        Optional<User> regUser = store.create(user);
        if (regUser.isEmpty()) {
            model.addAttribute("message", "Пользователь с таким именем уже существует!");
            return "redirect:/formAddUser?fail=true";
        }
        return "redirect:/items";
    }

    @GetMapping("/loginPage")
    public String loginPage(
            Model model,
            @RequestParam(name = "fail", required = false) Boolean fail,
            HttpSession session
    ) {
        User user = (User) session.getAttribute("name");
        if (user == null) {
            user = new User();
            user.setName("Гость");
        }
        model.addAttribute("users", user);
        model.addAttribute("fail", fail != null);
        model.addAttribute("user",
                new User(0, "Введите имя",
                        "Введите пароль"));
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute User user, HttpServletRequest req) {
        Optional<User> userDb = store.findUserByNameAndPwd(
                user.getName(), user.getPassword()
        );
        if (userDb.isEmpty()) {
            return "redirect:/loginPage?fail=true";
        }
        HttpSession session = req.getSession();
        session.setAttribute("user", userDb.get());
        return "redirect:/items";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/loginPage";
    }
}