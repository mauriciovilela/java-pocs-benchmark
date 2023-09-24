package com.msv.course.service;

import com.msv.course.model.Order;
import com.msv.course.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersRepository ordersRepository;

    public Order create() {
        Order order = new Order();
        order.setQuantity(new Random().nextInt());
        order.setPrice(BigDecimal.valueOf(new Random().nextFloat()));
        return ordersRepository.save(order);
    }

    public Optional<Order> findById(Long id) {
        return ordersRepository.findById(id);
    }

}