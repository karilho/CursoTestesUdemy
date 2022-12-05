package com.example.cursotestesudemy.resources.Exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class StandardError {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String path;

}
