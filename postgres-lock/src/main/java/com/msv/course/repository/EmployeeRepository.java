package com.msv.course.repository;

import com.msv.course.model.Employee;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.hibernate.LockOptions;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

    @QueryHints({
        @QueryHint(name = "javax.persistence.lock.timeout", value = LockOptions.SKIP_LOCKED + "")
    })
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select e From Employee e Where e.status = :status")
    Page<Employee> findByStatusSkipLock(@Param("status") String status, Pageable pageable);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Page<Employee> findByStatus(@Param("status") String status, Pageable pageable);

}