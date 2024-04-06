package com.isekai.ssgserver.delivery.entity;

import com.isekai.ssgserver.common.BaseEntity;
import com.isekai.ssgserver.deliveryAddress.entity.DeliveryAddress;
import com.isekai.ssgserver.order.entity.Order;

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
@Table(name = "delivery")
public class Delivery extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_id")
	private Long deliveryId;

	private String uuid;

	private int status;

	@Column(name = "deliveryType")
	private byte deliveryType;

	private String seller;

	@Column(name = "origin_price")
	private int originPrice;

	@Column(name = "buy_price")
	private int buyPrice;

	@Column(name = "delivery_fee")
	private int deliveryFee;

	@Column(name = "delivery_company")
	private String deliveryCompany;

	@Column(name = "delivery_code")
	private String deliveryCode;

	@Column(name = "delivery_message")
	private String deliveryMessage;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orders_id", nullable = false)
	private Order order;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "deliver_address_id")
	private DeliveryAddress deliveryAddress;

	@Builder
	public Delivery(int status, String uuid, byte deliveryType, String seller, int buyPrice, int originPrice,
		int deliveryFee,
		String deliveryCompany,
		String deliveryCode, String deliveryMessage, Order order, DeliveryAddress deliveryAddress) {
		this.status = status;
		this.uuid = uuid;
		this.deliveryType = deliveryType;
		this.seller = seller;
		this.buyPrice = buyPrice;
		this.originPrice = originPrice;
		this.deliveryFee = deliveryFee;
		this.deliveryCompany = deliveryCompany;
		this.deliveryCode = deliveryCode;
		this.deliveryMessage = deliveryMessage;
		this.order = order;
		this.deliveryAddress = deliveryAddress;
	}
}
