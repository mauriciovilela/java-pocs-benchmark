package com.msv.course.service;

import com.msv.course.model.PessimistLockEntity;
import com.msv.course.repository.PessimistLockRepository;
import com.msv.course.model.Message;
import com.msv.course.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PessimistLockService {

    private final PessimistLockRepository pessimistLockRepository;
    private final MessageRepository messageRepository;

    public PessimistLockEntity create() {
        PessimistLockEntity entity = new PessimistLockEntity();
        entity.setUpdateDate(null);
        entity.setName(UUID.randomUUID().toString());
        return pessimistLockRepository.save(entity);
    }

    @Transactional
    public void processWithPessimisticLock(Long id) {
        pessimistLockRepository.findWithLockingById(id).ifPresent(order -> {
            order.setUpdateDate(LocalDateTime.now());
            // add record from message consumed
            messageRepository.save(Message.builder()
                    .creationDate(LocalDateTime.now())
                    .text(String.format("PESSIMISTIC id=%d", id))
                    .build());
            log.info("PESSIMISTIC lock id={}", order.getId());
        });
    }

}
