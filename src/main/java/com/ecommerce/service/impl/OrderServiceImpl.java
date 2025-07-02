package com.ecommerce.service.impl;

import com.ecommerce.model.*;
import com.ecommerce.repository.*;
import com.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    private final OrderItemRepository orderItemRepository;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, CartRepository cartRepository, UserRepository userRepository, OrderItemRepository orderItemRepository) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.userRepository = userRepository;
        this.orderItemRepository = orderItemRepository;
    }

    @Override
    public Order placeOrder(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Cart cart = cartRepository.findAll().stream().filter(c -> c.getUser().getId().equals(userId)).findFirst().orElseThrow(() -> new RuntimeException("Cart not found"));
        Order newOrder = Order.builder().user(user).orderDate(LocalDateTime.now()).status("PLACED").build();
        newOrder = orderRepository.save(newOrder);
        final Order finalOrder = newOrder;
        cart.getCartItems().forEach(cartItem -> {
            OrderItem orderItem = OrderItem.builder()
                .order(finalOrder)
                .product(cartItem.getProduct())
                .quantity(cartItem.getQuantity())
                .price(cartItem.getProduct().getPrice())
                .build();
            orderItemRepository.save(orderItem);
        });
        cart.getCartItems().clear();
        cartRepository.save(cart);
        return finalOrder;
    }

    @Override
    public List<Order> getOrdersByUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return orderRepository.findAll().stream().filter(o -> o.getUser().getId().equals(userId)).toList();
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new RuntimeException("Order not found"));
    }
} 