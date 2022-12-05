package com.example.cursotestesudemy.config;

import com.example.cursotestesudemy.entities.Users;
import com.example.cursotestesudemy.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

@Configuration
@Profile("local")
public class LocalConfig {

    @Autowired
    private UsersRepository repository;

    @Bean
    public void startDB() {
        Users u1 = new Users(1, "Maria Brown", "soakso@gmail.com", "xablau");
        Users u2 = new Users(2, "SASASA Brown", "retetert@gmail.com", "rest");

        repository.saveAll(List.of(u1 , u2));

    }
}
