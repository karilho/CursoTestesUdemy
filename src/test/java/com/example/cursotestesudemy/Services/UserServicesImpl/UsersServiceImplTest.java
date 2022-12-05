package com.example.cursotestesudemy.Services.UserServicesImpl;

import com.example.cursotestesudemy.Services.Exceptions.DataIntegrityViolationException;
import com.example.cursotestesudemy.Services.Exceptions.ObjectNotFoundException;
import com.example.cursotestesudemy.entities.Users;
import com.example.cursotestesudemy.entities.dto.UsersDTO;
import com.example.cursotestesudemy.repositories.UsersRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UsersServiceImplTest {
    public static final Integer ID = 1;
    public static final String WALDIN = "waldin";
    public static final String WALWK_GMAIL_COM = "walwk@gmail.com";
    public static final String PASSWORD = "32fe";
    public static final int INDEX = 0;


    @InjectMocks
    private UsersServiceImpl service;

    @Mock
    private UsersRepository repository;

    @Mock
    private ModelMapper mapper;

    private Users users;

    private UsersDTO usersDTO;

    private Optional<Users> optionalUsers;




    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startUsers();
    }

    @Test
    void whenFindByIdThenReturnAnUserInstance() {
        //Quando for chamado, retorne o optionalusers.
        when(repository.findById(anyInt())).thenReturn(optionalUsers);
        Users response = service.findById(ID);

        // Garantir que meu assert não será NULO, depois eu chamo o comparativo.
        assertNotNull(response);
        // assegurar que as informações são iguais, o primeiro e o segundo argumento do assert.
        // O primeiro é o que eu estou esperando recebr, o segundo é o atual.
        assertEquals(Users.class, response.getClass());
        // Garante que o ID será igual, comparando
        assertEquals(ID, response.getId());

        // Teste FALHO
       // Assertions.assertEquals(UsersDTO.class, response.getClass());
    }

    @Test
    void whenFindByIdThenReturnAnObjectNotFoundException(){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));

        try {
            service.findById(ID);
        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }
    }

    @Test
    void whenFindAllThenReturnAnListOfUsers() {
        when(repository.findAll()).thenReturn(List.of(users));

        List<Users> response = service.findAll();

        assertNotNull(response);

        //Tamanho da lista, no caso uma lista
        assertEquals(1, response.size());
        // Um objeto USUARIO da lista.
        assertEquals(Users.class, response.get(INDEX).getClass());

        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(WALDIN, response.get(INDEX).getName());
        assertEquals(WALWK_GMAIL_COM, response.get(INDEX).getEmail());
        assertEquals(PASSWORD, response.get(INDEX).getPassword());

    }

    @Test
    void whenCreateThenReturnSucess() {
        when(repository.save(any())).thenReturn(users);

        Users response = service.create(usersDTO);

        assertNotNull(response);

        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(WALDIN, response.getName());
        assertEquals(WALWK_GMAIL_COM, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenCreateThenReturnDataViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUsers);

        try {
            optionalUsers.get().setId(ID);
            service.create(usersDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Já existe o email cadastrado!", ex.getMessage());
        }
    }

    @Test
    void whenUpdateThenReturnSucess() {
        when(repository.save(any())).thenReturn(users);

        Users response = service.update(usersDTO);

        assertNotNull(response);

        assertEquals(Users.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(WALDIN, response.getName());
        assertEquals(WALWK_GMAIL_COM, response.getEmail());
        assertEquals(PASSWORD, response.getPassword());
    }

    @Test
    void whenUpdateThenReturnDataViolationException() {
        when(repository.findByEmail(anyString())).thenReturn(optionalUsers);

        try {
            optionalUsers.get().setId(2);
            service.update(usersDTO);
        } catch (Exception ex) {
            assertEquals(DataIntegrityViolationException.class, ex.getClass());
            assertEquals("Já existe o email cadastrado!", ex.getMessage());
        }
    }

    @Test
    void deleteWithSucess() {
        when(repository.findById(anyInt())).thenReturn(optionalUsers);
        doNothing().when(repository).deleteById(anyInt());

        service.delete(ID);

        verify(repository,times(1)).deleteById(anyInt());
    }

    @Test
    void deleteWithNoObjectFoundException (){
        when(repository.findById(anyInt())).thenThrow(new ObjectNotFoundException("Objeto não encontrado"));
        try {
            service.delete(ID);
        } catch (Exception ex){
            assertEquals(ObjectNotFoundException.class, ex.getClass());
            assertEquals("Objeto não encontrado", ex.getMessage());
        }
    }


    private void startUsers (){
        users = new Users(ID, WALDIN, WALWK_GMAIL_COM, PASSWORD);
        usersDTO = new UsersDTO(ID, WALDIN, WALWK_GMAIL_COM, PASSWORD);
        optionalUsers = Optional.of(new Users(ID, WALDIN, WALWK_GMAIL_COM, PASSWORD));
    }
}