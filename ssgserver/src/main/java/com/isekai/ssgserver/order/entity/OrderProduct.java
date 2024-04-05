package com.isekai.ssgserver.order.entity;

import com.isekai.ssgserver.delivery.entity.Delivery;

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
@ToString
@Getter
@Table(name = "order_product")
public class OrderProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_product_id")
	private Long orderProductId;

	@Column(name = "count", nullable = false)
	private int count;

	@Column(name = "buy_price", nullable = false)
	private int buyPrice;

	@Column(name = "is_confirm", nullable = false)
	private boolean is_confirm;

	@Column(name = "product_code")
	private String productCode;

	@Column(name = "origin_price")
	private int originPrice;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "delivery_id", nullable = false)
	private Delivery delivery;

	@Builder
	public OrderProduct(int count, int buyPrice, int originPrice, boolean is_confirm, String productCode,
		Delivery delivery) {
		this.count = count;
		this.buyPrice = buyPrice;
		this.originPrice = originPrice;
		this.is_confirm = is_confirm;
		this.productCode = productCode;
		this.delivery = delivery;
	}
}
