package com.tqi.course.service;

import com.tqi.course.model.Message;
import com.tqi.course.model.OptimisticLockEntity;
import com.tqi.course.repository.MessageRepository;
import com.tqi.course.repository.OptimisticLockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptimisticLockService {

    private final OptimisticLockRepository optimisticLockRepository;
    private final MessageRepository messageRepository;

    public OptimisticLockEntity create() {
        OptimisticLockEntity entity = new OptimisticLockEntity();
        entity.setUpdateDate(null);
        entity.setName(UUID.randomUUID().toString());
        return optimisticLockRepository.save(entity);
    }

    @Transactional
    public void processWithOptimisticLock(Long id) {
        try {
            optimisticLockRepository.findWithLockingById(id).ifPresent(order -> {
                order.setUpdateDate(LocalDateTime.now());
            });
            // add record from message consumed
            messageRepository.save(Message.builder()
                    .creationDate(LocalDateTime.now())
                    .text(String.format("OPTIMISTIC id=%d", id))
                    .build());
            log.info("OPTIMISTIC lock id={}", id);
        } catch (ObjectOptimisticLockingFailureException e) {
            log.warn("Is locked id={} error={}", id, e.getMessage());
        }
    }

}
