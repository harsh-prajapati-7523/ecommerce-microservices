package com.ecom.harsh.authservice.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UsersDTO {


    @NotBlank(message = "Name can not be blank")
    @Size(min=2, max=100, message = "Name must be between 2 and 100 characters")
    private String name;

    @NotBlank(message = "Username can not be blank")
    @Size(min=5, max=100, message = "Username must be between 5 and 100 characters")
    private String username;
    
    @NotBlank(message = "Password can not be blank")
    @Size(min=5, max=100, message = "Password must be between 5 and 100 characters")
    private String password;

    private Role role; // ADMIN or USER  
    

}


