package com.isekai.ssgserver.product.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isekai.ssgserver.product.entity.ReviewScore;

public interface ReviewScoreRepository extends JpaRepository<ReviewScore, Long> {

	Optional<ReviewScore> findByProductCode(String productCode);
}
