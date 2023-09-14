package com.tqi.course.service;

import com.tqi.course.model.Order;
import com.tqi.course.repository.OrdersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
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

    @Transactional
    public void processWithTopic(Long id) {
        log.info("TOPIC message consumed id={}", id);
    }

}