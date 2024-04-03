package com.isekai.ssgserver.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
