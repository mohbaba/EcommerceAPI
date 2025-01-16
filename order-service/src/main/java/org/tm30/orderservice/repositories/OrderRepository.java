package org.tm30.orderservice.repositories;

import org.springframework.stereotype.Repository;
import org.tm30.orderservice.exceptions.OrderNotFoundException;
import org.tm30.orderservice.models.Order;

import java.util.*;

@Repository
public class OrderRepository {
    private final Map<Long, Order> productRepo = new HashMap<>();
    private long nextId = 1;

    public Order save(Order order) {
        if (order.getId() != null) {
            update(order.getId(), order);
        } else {
            order.setId(nextId++);
        }
        productRepo.put(order.getId(), order);
        return order;
    }

    public Optional<Order> findById(Long id) {
        return Optional.ofNullable(productRepo.get(id));
    }

    public List<Order> findAll() {
        return new ArrayList<>(productRepo.values());
    }

    public Order update(Long id, Order order) {
        if (!productRepo.containsKey(id)) {
            throw new OrderNotFoundException("Product not found");
        }
        order.setId(id);
        productRepo.put(id, order);
        return order;
    }

    public void delete(Long id) {
        if (!productRepo.containsKey(id)) {
            throw new OrderNotFoundException("Product not found");
        }
        productRepo.remove(id);
    }

    public void clear() {
        productRepo.clear();
        nextId = 1;
    }
}
