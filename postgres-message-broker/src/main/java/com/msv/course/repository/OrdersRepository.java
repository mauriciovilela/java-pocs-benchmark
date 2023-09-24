package com.msv.course.repository;

import com.msv.course.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Long> {

}