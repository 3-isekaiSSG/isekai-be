package com.isekai.ssgserver.bundle.entity;

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
@Table(name = "bundle")
public class Bundle {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bundle_id")
	private Long bundleId;

	@Column(name = "outer_name", nullable = false)
	private String outerName;

	@Column(name = "inner_name", nullable = false)
	private String innerName;

	@Column(nullable = false)
	private String code;

	@Column(name = "min_price", nullable = false)
	private int minPrice;

	@Column(name = "review_count", nullable = false)
	private Long reviewCount;

	@Column(name = "avg_score", nullable = false)
	private double avgScore;

	@Column(name = "buy_count")
	private Long buyCount;

	@Column(name = "delivery_type", nullable = false)
	private byte deliveryType;

	@Column(name = "image_url")
	private String imageUrl;
}
