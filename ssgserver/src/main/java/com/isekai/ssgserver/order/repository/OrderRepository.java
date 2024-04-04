package com.isekai.ssgserver.order.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.order.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

	@Query("SELECT MAX(o.ordersId) FROM Order o")
	Optional<Long> findLastOrdersId();
}
