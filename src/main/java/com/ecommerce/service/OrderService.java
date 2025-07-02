package com.ecommerce.service;

import com.ecommerce.model.Order;
import java.util.List;
 
public interface OrderService {
    Order placeOrder(Long userId);
    List<Order> getOrdersByUser(Long userId);
    Order getOrderById(Long orderId);
} 