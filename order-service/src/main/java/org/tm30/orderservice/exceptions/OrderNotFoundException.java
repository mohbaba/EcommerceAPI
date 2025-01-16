package org.tm30.orderservice.exceptions;

public class OrderNotFoundException extends EcommerceException{
    public OrderNotFoundException(String message) {
        super(message);
    }
}
