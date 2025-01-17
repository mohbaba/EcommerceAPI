package org.tm30.orderservice.services;


import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tm30.orderservice.dtos.requests.CreateOrderRequest;
import org.tm30.orderservice.eventpublishers.GetUserProducer;
import org.tm30.orderservice.models.Order;
import org.tm30.orderservice.repositories.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Setter
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private GetUserProducer getUserProducer;
    @Override
    public Order createOrder(CreateOrderRequest request) {
        log.info("Order Service currently running");
        ModelMapper modelMapper = new ModelMapper();
        Order order = modelMapper.map(request, Order.class);
        order.setTotalPrice(BigDecimal.valueOf(request.getTotalPrice()));
        return orderRepository.save(order);
    }

    @Override
    public List<Order> getAllOrders() {
        log.info("Order Service currently running");
        return orderRepository.findAll();
    }

    private void getProduct(Long productId){
        getUserProducer.publishMessage(productId);
        getUserProducer.consumeGetProductMessage();
    }
}
