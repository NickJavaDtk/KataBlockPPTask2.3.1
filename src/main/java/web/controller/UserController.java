package web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import spring.model.User;
import spring.service.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping(produces = "text/plain; charset=UTF-8")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getStartPage(Model model) {
        List<User> userList = userService.getUserList();
        model.addAttribute("list", userList);
        return "index";
    }

    @GetMapping("/user/add")
    public String getAddUserPage(Model model) {
        model.addAttribute("newuser", new User());
        return "adduser";
    }

    @PostMapping("/user/add")
    public String addUser(@ModelAttribute("newuser") @Valid User users, Errors errors, SessionStatus status) {
        if (errors.hasErrors()) {
            return "adduser";
        }
        User user = users;
        userService.addUser(user);
        status.setComplete();
        return "redirect:/";
    }

    @GetMapping("/user/edit")
    public String getEditUserPage(@RequestParam("userId") String id, Model model) {
        User userTmp = userService.getUser(id);
        model.addAttribute("edituser", userTmp);
        return "edituser";
    }

    @PostMapping("/user/edit")
    public String editUser(@RequestParam("userId") String id, @ModelAttribute("edituser") User users) {
        userService.updateUser(id, users);
        return "redirect:/";
    }

    @GetMapping("user/delete")
    public String deleteUser(@RequestParam("userId") String id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
