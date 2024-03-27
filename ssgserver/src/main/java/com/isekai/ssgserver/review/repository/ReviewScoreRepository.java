package com.isekai.ssgserver.review.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.review.entity.ReviewScore;

@Repository
public interface ReviewScoreRepository extends JpaRepository<ReviewScore, Long> {

	List<ReviewScore> findByProductProductId(Long productId);
}
