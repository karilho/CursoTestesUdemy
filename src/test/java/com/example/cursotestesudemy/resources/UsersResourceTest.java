package com.example.cursotestesudemy.resources;

import com.example.cursotestesudemy.Services.UserServicesImpl.UsersServiceImpl;
import com.example.cursotestesudemy.entities.Users;
import com.example.cursotestesudemy.entities.dto.UsersDTO;
import com.example.cursotestesudemy.repositories.UsersRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UsersResourceTest {

    public static final Integer ID = 1;
    public static final String WALDIN = "waldin";
    public static final String WALWK_GMAIL_COM = "walwk@gmail.com";
    public static final String PASSWORD = "32fe";
    public static final int INDEX = 0;

    @InjectMocks
    private UsersResource resource;

    @Mock
    private UsersServiceImpl service;

    @Mock
    private ModelMapper mapper;

    private Users users = new Users();
    private UsersDTO usersDTO = new UsersDTO();
    private Optional<Users> optionalUsers;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsers();
        //Fixar o erro de ServletRequestAttributes
        HttpServletRequest mockRequest = new MockHttpServletRequest();
        ServletRequestAttributes servletRequestAttributes = new ServletRequestAttributes(mockRequest);
        RequestContextHolder.setRequestAttributes(servletRequestAttributes);
    }

    @Test
    void findByIdWithSucess() {
        when(service.findById(anyInt())).thenReturn(users);
        // Usei dois any pois no findbyID, ele vai converter uma entidade que é o return do service
        //findbyid para o USERDTO, então passo any any
        when(mapper.map(any(), any())).thenReturn(usersDTO);

        ResponseEntity<UsersDTO> response = resource.findById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsersDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(WALDIN, response.getBody().getName());
        assertEquals(WALWK_GMAIL_COM, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());
    }

    @Test
    void findByIdWithNotFound() {
        when(service.findById(anyInt())).thenThrow(RuntimeException.class);

        Assertions.assertThrows(RuntimeException.class, () -> {
            resource.findById(ID);
        });
    }

    @Test
    void findAllThenReturnAListOfUserDto() {
        when(service.findAll()).thenReturn(List.of(users));
        when(mapper.map(any(), any())).thenReturn(usersDTO);

        ResponseEntity<List<UsersDTO>> response = resource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());

        //Verifica se o endpoint tá ok.
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UsersDTO.class, response.getBody().get(INDEX).getClass());

        // Verifica se o ID está correto
        assertEquals(ID, response.getBody().get(INDEX).getId());
        assertEquals(WALDIN, response.getBody().get(INDEX).getName());
        assertEquals(WALWK_GMAIL_COM, response.getBody().get(INDEX).getEmail());
        assertEquals(PASSWORD, response.getBody().get(INDEX).getPassword());

    }


    @Test
    void whenCreateThenReturnCreated() {
        when(service.create(any())).thenReturn(users);

        ResponseEntity<UsersDTO> response = resource.create(usersDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertNotNull(response.getHeaders().get("Location"));
    }

    @Test
    void whenUpdateThenReturnSucess() {

        when(service.update(usersDTO)).thenReturn(users);
        when(mapper.map(any(), any())).thenReturn(usersDTO);

        ResponseEntity<UsersDTO> response = resource.update(ID,usersDTO);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UsersDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(WALDIN, response.getBody().getName());
        assertEquals(WALWK_GMAIL_COM, response.getBody().getEmail());
        assertEquals(PASSWORD, response.getBody().getPassword());

    }

    @Test
    void whenDeleteThenReturnSucess() {
        //Não faça nada quando meu service chamar delete com qlquer int
        doNothing().when(service).delete(anyInt());

        ResponseEntity<UsersDTO> response = resource.delete(ID);

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        //Veriricar que ele foi chamado SOMENTE UMA VEZ
        verify(service, times(1)).delete(anyInt());
    }

    private void startUsers() {
        users = new Users(ID, WALDIN, WALWK_GMAIL_COM, PASSWORD);
        usersDTO = new UsersDTO(ID, WALDIN, WALWK_GMAIL_COM, PASSWORD);
        optionalUsers = Optional.of(new Users(ID, WALDIN, WALWK_GMAIL_COM, PASSWORD));
    }
}