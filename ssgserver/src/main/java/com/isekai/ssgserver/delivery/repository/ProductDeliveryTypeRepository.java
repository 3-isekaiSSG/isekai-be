package com.isekai.ssgserver.delivery.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.delivery.entity.DeliveryType;
import com.isekai.ssgserver.delivery.entity.ProductDeliveryType;
import com.isekai.ssgserver.product.entity.Product;

@Repository
public interface ProductDeliveryTypeRepository extends JpaRepository<ProductDeliveryType, Long> {
	@Query("SELECT pdt.deliveryType FROM ProductDeliveryType pdt WHERE(pdt.product.productId = :productId)")
	List<DeliveryType> findByProductId(Long productId);

	Optional<ProductDeliveryType> findFirstByProduct(Product product);
}

