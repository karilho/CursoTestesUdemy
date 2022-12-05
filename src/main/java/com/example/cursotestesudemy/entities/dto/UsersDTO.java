package com.example.cursotestesudemy.entities.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsersDTO {
    private Integer id;
    private String name;
    private String email;

    //Faz com o que DTO aceita somente leituras.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

}
