package ru.kata.spring.boot_security.demo.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.dao.RoleDao;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.annotation.PostConstruct;
import java.util.HashSet;
import java.util.Set;

@Component
public class NewUsers {
    private final UserService userService;

    private final RoleDao roleDao;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public NewUsers(UserService userService, RoleDao roleDao, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.roleDao = roleDao;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createUsers() {
        Set<Role> set = new HashSet<>();
        Set<Role> set1 = new HashSet<>();
        Set<Role> set2 = new HashSet<>();

        Role user = new Role("ROLE_USER");
        Role admin = new Role("ROLE_ADMIN");

        roleDao.addRole(user);
        roleDao.addRole(admin);

        set.add(admin);
        set1.add(user);
        set2.add(admin);
        set2.add(user);


        for (int i = 0; i < 10; i++) {
            userService.addNewUser(new User("admin" + i, "surname" + i, "email" + i,
                    i, passwordEncoder.encode("1"), set));
        }
        for (int i = 11; i < 16; i++) {
            userService.addNewUser(new User("user" + i, "surname" + i, "email" + i,
                    i, passwordEncoder.encode("1"), set1));
        }

        userService.addNewUser(new User("userAdmin", "surname", "email",
                1, passwordEncoder.encode("1"), set2));
    }

}