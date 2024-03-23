package com.isekai.ssgserver.review.entity;

import com.isekai.ssgserver.product.entity.Product;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Table(name = "review_score")
public class ReviewScore {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_score_id")
	private Long reviewScoreId;

	@Column(name = "review_count", nullable = false)
	private Long reviewCount;

	@Column(name = "total_score", nullable = false)
	private Long totalScore;

	@Column(name = "avg_score", nullable = false)
	private double avgScore;

	// 연관 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

}
