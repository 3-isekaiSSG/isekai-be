package com.isekai.ssgserver.product.entity;

import java.time.LocalDateTime;

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
@Table(name = "product")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_id")
	private Long productId;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "price", nullable = false)
	private int price;

	@Column(name = "code", nullable = false)
	private String code;

	@Column(name = "product_detail", columnDefinition = "TEXT")
	private String productDetail;

	@Column(name = "adult_sales", nullable = false)
	private int adultSales;

	@Column(name = "status", nullable = false)
	private int status;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	// @PrePersist
	// public void prePersist() {
	// 	this.status = true;
	// }
}
