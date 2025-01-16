package org.tm30.orderservice.services;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.tm30.orderservice.dtos.requests.CreateOrderRequest;
import org.tm30.orderservice.models.Order;
import org.tm30.orderservice.models.Status;
import org.tm30.orderservice.repositories.OrderRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class OrderServiceImplTest{

    @Autowired
    private OrderServiceImpl orderService;

    private OrderRepository orderRepository;

    @BeforeEach
    void setUp() {
        orderRepository = new OrderRepository();
        orderService = new OrderServiceImpl();
        orderService.setOrderRepository(orderRepository);
    }

    @Test
    void testCreateOrder() {
        CreateOrderRequest request = new CreateOrderRequest(1L, 5, 5000.00, Status.PENDING);

        Order result = orderService.createOrder(request);

        assertNotNull(result);
        assertEquals(request.getQuantity(), result.getQuantity());
    }
}