package com.example.cursotestesudemy.resources;

import com.example.cursotestesudemy.Services.UsersService;
import com.example.cursotestesudemy.entities.Users;
import com.example.cursotestesudemy.entities.dto.UsersDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/users")
public class UsersResource {

    public static final String ID = "/{id}";
    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UsersService service;

    @GetMapping(value = ID)
    public ResponseEntity<UsersDTO> findById(@PathVariable Integer id) {
        return ResponseEntity.ok().body(mapper.map(service.findById(id), UsersDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<UsersDTO>> findAll(){
        // Essa linha pega a lista padrão e retorna em dto, fazendo a conversão para uma list.
        List<UsersDTO> listDto = service.findAll()
                .stream()
                .map(x-> mapper.map(x, UsersDTO.class))
                .collect(Collectors.toList());
        return ResponseEntity.ok().body(listDto);
    }

    @PostMapping
    public ResponseEntity<UsersDTO> create(@RequestBody UsersDTO objDTO){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID)
                .buildAndExpand(service.create(objDTO).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<UsersDTO> update(@PathVariable Integer id, @RequestBody UsersDTO objDTO){
        objDTO.setId(id);
        Users newObj = service.update(objDTO);
        return ResponseEntity.ok().body(mapper.map(newObj, UsersDTO.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UsersDTO> delete(@PathVariable Integer id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
