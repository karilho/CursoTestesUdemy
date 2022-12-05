package com.example.cursotestesudemy.Services.Exceptions;

public class ObjectNotFoundException extends RuntimeException{

    public ObjectNotFoundException (String message){
        super(message);
    }
}
