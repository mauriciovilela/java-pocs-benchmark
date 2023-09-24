package com.msv.course.service;

import com.msv.course.repository.OrdersRepository;
import com.msv.course.model.Message;
import com.msv.course.model.Order;
import com.msv.course.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrdersService {

    private final OrdersRepository ordersRepository;
    private final MessageRepository messageRepository;

    public Order create() {
        Order order = new Order();
        order.setQuantity(new Random().nextInt());
        order.setPrice(BigDecimal.valueOf(new Random().nextFloat()));
        return ordersRepository.save(order);
    }

    @Transactional
    public void processWithTopic(Long id) {
        ordersRepository.findById(id).ifPresent(order -> {
            log.info("TOPIC message consumed id={}", order.getId());
            // add record from message consumed
            messageRepository.save(Message.builder()
                    .creationDate(LocalDateTime.now())
                    .text(String.format("OPTIMISTIC id=%d", id))
                    .build());
        });
    }

}