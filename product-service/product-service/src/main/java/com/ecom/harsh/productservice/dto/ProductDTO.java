package com.ecom.harsh.productservice.dto;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;

// import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    
    // @NotBlank(message = "Product name can not be blank")
    // @Size(min=2, max=100, message = "Product name must be between 2 and 100 characters")
    private String name;


    public String getName() {
        return name;
    }   
    public void setName(String name) {
        this.name = name;
    }   

    // @NotBlank(message = "Description cannot be blank")
    // @Size(min = 5, max = 255, message = "Description must be between 5 and 255 characters")
    private String description;
    
    // @NotNull(message = "Price is required")
    // @Min(value = 1, message = "Price must be at least 1")
    private Double price;

    // @NotNull(message = "Stock quantity is required")
    // @Min(value = 0, message = "Stock quantity cannot be negative")
    // @Max(value = 1000, message = "Stock quantity cannot exceed 1000")
    private Integer stockQuantity;
    // private LocalDateTime createdAt;
    // private LocalDateTime updatedAt;
}
