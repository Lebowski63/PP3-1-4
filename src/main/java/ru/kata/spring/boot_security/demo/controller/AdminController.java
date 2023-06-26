package ru.kata.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;


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
    public String getUsers(Model model) {
        model.addAttribute("user", userService.getAllUsers());
        return "admin";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") Long id) {
        userService.delete(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.getById(id));
        model.addAttribute("roles", roleService.listRoles());
        return "edit";
    }

    @PatchMapping("/edit/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") User user) {
        userService.update(id,user);
        return "redirect:/admin";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user ,Model model) {
        model.addAttribute("roles", roleService.listRoles());
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegistration(@ModelAttribute("user") User user) {
        userService.save(user);
        return "redirect:/admin";
    }
}