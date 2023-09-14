package com.tqi.course.repository;

import com.tqi.course.model.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrdersRepository extends CrudRepository<Order, Long> {

}