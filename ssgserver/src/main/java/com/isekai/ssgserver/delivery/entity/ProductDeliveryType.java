package com.isekai.ssgserver.delivery.entity;

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
@Table(name = "product_delivery_type")
public class ProductDeliveryType {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_delivery_type_id")
	private Long productDeliveryTypeId;

	@Column(name = "product_code")
	private String productCode;

	// 연관 관계

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_type_id", nullable = false)
	private DeliveryType deliveryType;
}
