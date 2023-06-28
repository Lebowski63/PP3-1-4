package ru.kata.spring.boot_security.demo.configs;

import org.hibernate.internal.build.AllowPrintStacktrace;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class RoleAndUser {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RoleAndUser(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Bean
    public void createRoleAndUser() {

        Role admin = new Role(1L, "ROLE_ADMIN");
        Role user = new Role(2L, "ROLE_USER");
        roleRepository.saveAll(List.of(admin, user));

        List<Role> rolesOfAdmin = new ArrayList<>();
        List<Role> rolesOfUser = new ArrayList<>();

        Collections.addAll(rolesOfAdmin, admin, user);
        Collections.addAll(rolesOfUser, user);

        User adminUser = new User();
        adminUser.setUsername("admin");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        adminUser.setListRole(rolesOfAdmin);
        adminUser.setEmail("@admin");
        adminUser.setLastname("admin");
        userRepository.save(adminUser);
        User normalUser = new User();
        normalUser.setUsername("user");
        normalUser.setPassword(passwordEncoder.encode("user"));
        normalUser.setListRole(rolesOfUser);
        normalUser.setEmail("@user");
        normalUser.setLastname("user");
        userRepository.save(normalUser);
    }
}
