package org.tm30.orderservice.dtos.responses;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;
    private String name;
    private String description;
    private double price;
    private int stockQuantity;
}
