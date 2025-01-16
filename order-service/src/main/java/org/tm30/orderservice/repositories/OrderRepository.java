package org.tm30.orderservice.repositories;

import org.springframework.stereotype.Repository;
import org.tm30.orderservice.exceptions.OrderNotFoundException;
import org.tm30.orderservice.models.Order;

import java.util.*;

@Repository
public class OrderRepository {
    private final Map<Long, Order> orderRepo = new HashMap<>();
    private long nextId = 1;

    public Order save(Order order) {
        if (order.getId() != null) {
            update(order.getId(), order);
        } else {
            order.setId(nextId++);
        }
        orderRepo.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(orderRepo.get(id));
    }

    public List<Order> findAll() {
        return new ArrayList<>(orderRepo.values());
    }

    public Order update(Long id, Order order) {

        order.setId(id);
        orderRepo.put(id, order);
        return order;
    }

    public void delete(Long id) {
        if (!orderRepo.containsKey(id)) {
            throw new OrderNotFoundException("Order not found");
        }
        orderRepo.remove(id);
    }

    public void clear() {
        orderRepo.clear();
        nextId = 1;
    }
}
