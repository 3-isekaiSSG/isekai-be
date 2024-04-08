package com.isekai.ssgserver.review.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.review.entity.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
}
