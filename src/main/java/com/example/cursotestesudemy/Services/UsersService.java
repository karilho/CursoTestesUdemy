package com.example.cursotestesudemy.Services;

import com.example.cursotestesudemy.entities.Users;
import com.example.cursotestesudemy.entities.dto.UsersDTO;

import java.util.List;

public interface UsersService {
    Users findById(Integer id);

    List<Users> findAll();

    Users create(UsersDTO objDTO);

    Users update(UsersDTO objDTO);

    void delete(Integer id);

}
