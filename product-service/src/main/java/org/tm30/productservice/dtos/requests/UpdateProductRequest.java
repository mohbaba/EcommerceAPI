package org.tm30.productservice.dtos.requests;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProductRequest {
    @NotNull(message = "Id of product cannot be null")
    private Long id;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
}
