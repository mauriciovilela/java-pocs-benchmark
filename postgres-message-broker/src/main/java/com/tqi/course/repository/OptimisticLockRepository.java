package com.tqi.course.repository;

import com.tqi.course.model.OptimisticLockEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface OptimisticLockRepository extends CrudRepository<OptimisticLockEntity, Long> {

    @Lock(LockModeType.OPTIMISTIC_FORCE_INCREMENT)
    Optional<OptimisticLockEntity> findWithLockingById(Long id);

}