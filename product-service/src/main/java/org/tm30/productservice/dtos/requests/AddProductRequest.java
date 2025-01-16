package org.tm30.productservice.dtos.requests;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddProductRequest {
    private String description;

    @NotNull(message = "Price must not be null")
    @Positive(message = "Price must be a positive number")
    private double price;

    @NotNull(message = "Stock quantity must not be null")
    @Min(value = 0, message = "Stock quantity cannot be negative")
    private int stockQuantity;
}
