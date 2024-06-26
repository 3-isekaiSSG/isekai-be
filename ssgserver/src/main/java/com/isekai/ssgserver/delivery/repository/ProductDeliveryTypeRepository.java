package com.isekai.ssgserver.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.delivery.entity.ProductDeliveryType;

@Repository
public interface ProductDeliveryTypeRepository extends JpaRepository<ProductDeliveryType, Long> {
	@EntityGraph(attributePaths = {"deliveryType"})
	Optional<ProductDeliveryType> findByProductCode(String productCode);
}

