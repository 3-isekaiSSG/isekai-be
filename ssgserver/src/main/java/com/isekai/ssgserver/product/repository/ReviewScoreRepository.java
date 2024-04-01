package com.isekai.ssgserver.product.repository;

import com.isekai.ssgserver.product.entity.ReviewScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReviewScoreRepository extends JpaRepository<ReviewScore, Long> {

    Optional<ReviewScore> findByProductCode(String productCode);
}
