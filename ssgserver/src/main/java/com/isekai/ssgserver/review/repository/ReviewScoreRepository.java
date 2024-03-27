package com.isekai.ssgserver.review.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.review.entity.ReviewScore;

@Repository
public interface ReviewScoreRepository extends JpaRepository<ReviewScore, Long> {

	Optional<ReviewScore> findByProductProductId(Long productId);

	Optional<ReviewScore> findByProduct(Product product);
}
