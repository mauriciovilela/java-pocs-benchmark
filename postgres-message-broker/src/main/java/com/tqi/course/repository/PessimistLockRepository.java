package com.tqi.course.repository;

import com.tqi.course.model.PessimistLockEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;
import java.util.Optional;

public interface PessimistLockRepository extends CrudRepository<PessimistLockEntity, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints({@QueryHint(name = "javax.persistence.lock.timeout", value = "3000")})
    @Query("select a from PessimistLockEntity a where a.id = :id and updateDate is null")
    Optional<PessimistLockEntity> findWithLockingById(@Param("id") Long id);

}