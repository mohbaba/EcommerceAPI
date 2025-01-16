package org.tm30.orderservice.services;

import org.tm30.orderservice.dtos.requests.CreateOrderRequest;
import org.tm30.orderservice.models.Order;

import java.util.List;

public interface OrderService {
    Order createOrder(CreateOrderRequest request);
    List<Order> getAllOrders();
}
