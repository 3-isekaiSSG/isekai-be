package com.isekai.ssgserver.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.Discount;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

	List<Discount> findByProductProductId(Long productId);
}
