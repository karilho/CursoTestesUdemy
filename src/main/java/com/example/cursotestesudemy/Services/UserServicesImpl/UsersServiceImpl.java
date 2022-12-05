package com.example.cursotestesudemy.Services.UserServicesImpl;

import com.example.cursotestesudemy.Services.Exceptions.DataIntegrityViolationException;
import com.example.cursotestesudemy.Services.Exceptions.ObjectNotFoundException;
import com.example.cursotestesudemy.Services.UsersService;
import com.example.cursotestesudemy.entities.Users;
import com.example.cursotestesudemy.entities.dto.UsersDTO;
import com.example.cursotestesudemy.repositories.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersServiceImpl implements UsersService {

    @Autowired
    private UsersRepository repository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Users findById(Integer id) {
        Optional<Users> objU = repository.findById(id);
        return objU.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado"));

    }

    @Override
    public List<Users> findAll(){
        return repository.findAll();
    }

    @Override
    public Users create(UsersDTO objDTO) {
        findByEmail(objDTO);
        return repository.save(mapper.map(objDTO, Users.class));
    }

    @Override
    public Users update(UsersDTO objDTO) {
        findByEmail(objDTO);
        return repository.save(mapper.map(objDTO, Users.class));
    }

    @Override
    public void delete(Integer id) {
        findById(id);
        repository.deleteById(id);
    }

    private void findByEmail(UsersDTO objDTO){
        Optional<Users> user = repository.findByEmail(objDTO.getEmail());
        if(user.isPresent() && user.get().getId().equals(objDTO.getId())){
            throw new DataIntegrityViolationException("Já existe o email cadastrado!");
        }
    }
}


