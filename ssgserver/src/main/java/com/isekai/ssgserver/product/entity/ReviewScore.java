package com.isekai.ssgserver.product.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

	@Column(name = "product_code")
	private String productCode;

}
