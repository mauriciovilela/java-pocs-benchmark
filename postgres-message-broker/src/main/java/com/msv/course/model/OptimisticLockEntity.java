package com.msv.course.model;

import lombok.Data;
import org.hibernate.annotations.OptimisticLockType;
import org.hibernate.annotations.OptimisticLocking;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
@OptimisticLocking(type = OptimisticLockType.DIRTY)
@Table(name = "optimistic_lock")
public class OptimisticLockEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @Version
    private Long version;

    @Column(name = "update_date")
    LocalDateTime updateDate;

}