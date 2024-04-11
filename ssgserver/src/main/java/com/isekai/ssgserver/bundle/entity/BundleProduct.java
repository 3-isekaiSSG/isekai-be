package com.isekai.ssgserver.bundle.entity;

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
@Table(name = "bundle_product")
public class BundleProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "bundle_product_id")
	private long bundleProductId;

	@Column(name = "is_cart")
	private byte isCart;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "seq")
	private Integer seq;

	// 연관 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bundle_id", nullable = false)
	private Bundle bundle;
}
