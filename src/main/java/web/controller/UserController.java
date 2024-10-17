package web.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.DataBinder;
import org.springframework.validation.Errors;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import spring.model.User;
import spring.service.UserService;


import java.util.List;

@Controller
@RequestMapping(produces = "text/plain; charset=UTF-8")
public class UserController {

    private UserService userService;
    @Autowired
    private LocalValidatorFactoryBean validator;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @InitBinder
    private void initBinder(DataBinder binder) {
        if (binder.getTarget() instanceof User) {
            binder.setValidator(validator);
        }
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
    public String addUser(@ModelAttribute @Valid @Va User users, BindingResult result) {
        if (result.hasErrors()) {
            return "adduser";
        } else {
            User user = users;
            userService.addUser(user);
            return "redirect:/";
        }
    }

    @GetMapping("/user/edit")
    public String getEditUserPage(@RequestParam("userId") String id, Model model) {
        User userTmp = userService.getUser(id);
        model.addAttribute("edituser", userTmp);
        return "edituser";
    }

    @PostMapping("/user/edit")
    public String editUser(@ModelAttribute @Valid User users, @RequestParam("userId") String id, Errors errors) {
        if (errors.hasErrors()) {
            String s = "dd";
            return "index";

        } else {
            String s = "dfsdfg";
            userService.updateUser(id, users);
        }
        return "redirect:/";
    }

    @GetMapping("user/delete")
    public String deleteUser(@RequestParam("userId") String id) {
        userService.deleteUser(id);
        return "redirect:/";
    }
}
