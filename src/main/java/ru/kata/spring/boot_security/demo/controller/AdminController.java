package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }


    @GetMapping
    public String getUsers(Model model, Principal principal) {
        model.addAttribute("user", userService.getAllUsers());
        model.addAttribute("anotherUser",userService.findByUsername(principal.getName()));
        return "admin";
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam(value = "id") Long id) {
        userService.delete(id);
        return "redirect:/admin/";
    }


    @PutMapping("/update/{id}")
    public String updateUser(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin/";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user ,Model model) {
        model.addAttribute("roles", roleService.listRoles());
        return "redirect:/admin";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }
}