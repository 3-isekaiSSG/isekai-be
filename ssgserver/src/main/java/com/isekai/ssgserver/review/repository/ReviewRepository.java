package com.isekai.ssgserver.review.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
	@Query("SELECT r.createdAt, r.accountId, r.productId, r.reviewContent, r.reviewImage, r.score, r.reviewId " +
		"FROM Review r WHERE r.productId = :productId")
	Page<Object[]> findByProductId(@Param("productId") Long productId, Pageable pageable);

	Review findByReviewId(Long reviewId);

	Long countByProductId(Long productId);

	@Query("SELECT COUNT(r) FROM Review r WHERE r.productId = :productId AND r.reviewImage IS NOT NULL")
	Long countByProductIdAndImage(@Param("productId") Long productId);
}
