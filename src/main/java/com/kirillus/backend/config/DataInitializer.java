package com.kirillus.backend.config;

import com.kirillus.backend.entity.Role;
import com.kirillus.backend.entity.User;
import com.kirillus.backend.repository.RoleRepository;
import com.kirillus.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        try {
            // Создаем роли, если их еще нет
            if (roleRepository.findByName("admin").isEmpty()) {
                Role adminRole = new Role();
                adminRole.setName("admin");
                adminRole.setDescription("Администратор системы");
                roleRepository.save(adminRole);
            }

            if (roleRepository.findByName("operator").isEmpty()) {
                Role operatorRole = new Role();
                operatorRole.setName("operator");
                operatorRole.setDescription("Оператор системы");
                roleRepository.save(operatorRole);
            }

            // Создаем тестового пользователя, если его еще нет
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.findByName("admin").orElseThrow();
                User admin = new User();
                admin.setUsername("admin");
                admin.setPasswordHash(passwordEncoder.encode("admin"));
                admin.setLastName("Администратор");
                admin.setFirstName("Системный");
                admin.setMiddleName("");
                admin.setRole(adminRole);
                userRepository.save(admin);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации данных: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

