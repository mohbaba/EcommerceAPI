package org.tm30.productservice.exceptions;

public class ProductNotFoundException extends EcommerceException{
    public ProductNotFoundException(String message) {
        super(message);
    }
}
