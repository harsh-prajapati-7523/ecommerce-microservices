package com.ecom.harsh.authservice.config;

import com.ecom.harsh.authservice.model.Role;
import com.ecom.harsh.authservice.model.Users;
import com.ecom.harsh.authservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class AdminUserBootstrapConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    public CommandLineRunner createAdminUser(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() == 0) {
                Users admin = new Users(
                    "Admin", // name
                    "admin", // username
                    passwordEncoder.encode("admin123"), // password (change after first login)
                    Role.ADMIN // role
                );
                userRepository.save(admin);
                System.out.println("Default admin user created: username=admin, password=admin123");
            }
        };
    }
}
