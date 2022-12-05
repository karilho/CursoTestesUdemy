package com.example.cursotestesudemy.resources.Exceptions;

import com.example.cursotestesudemy.Services.Exceptions.DataIntegrityViolationException;
import com.example.cursotestesudemy.Services.Exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.exceptions.base.MockitoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;

import javax.xml.crypto.Data;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ResourcesExceptionHandlerTest {

    @InjectMocks
    private ResourcesExceptionHandler resourcesExceptionHandler;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

    }

    @Test
    void whenObjectNotFoundExceptionThenReturnAnResponseEntity() {
        ResponseEntity<StandardError> response = resourcesExceptionHandler
                .objectNotFound(
                        new ObjectNotFoundException("Object not found"),
                        new MockHttpServletRequest());


        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("Object not found", response.getBody().getError());
        assertEquals(404, response.getBody().getStatus());
        assertNotEquals("/users/2", response.getBody().getPath());
        assertNotEquals(LocalDateTime.now(), response.getBody().getTimestamp());

    }



    @Test
    void dataIntegrityViolation() {
        ResponseEntity<StandardError> response = resourcesExceptionHandler
                .dataIntegrityViolation(
                        new DataIntegrityViolationException("E-mail já cadastrado"),
                        new MockHttpServletRequest());

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(StandardError.class, response.getBody().getClass());
        assertEquals("E-mail já cadastrado", response.getBody().getError());
        assertEquals(400, response.getBody().getStatus());
    }
}