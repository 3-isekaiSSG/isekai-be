package com.isekai.ssgserver.order.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isekai.ssgserver.order.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long> {

	List<OrderProduct> findAllByDeliveryDeliveryId(Long deliveryId);
}
