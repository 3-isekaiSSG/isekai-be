package com.isekai.ssgserver.deliveryAddress.entity;

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
@Table(name = "delivery_address")
public class DeliveryAddress {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "delivery_address_id")
	private long deliveryAddressId;

	@Column(name = "member_id")
	private long memeberId;

	@Column(name = "nickname", nullable = false)
	private String nickname;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "cellphone", nullable = false)
	private String cellphone;

	@Column(name = "telephone", nullable = true)
	private String telephone;

	@Column(name = "zip_code", nullable = false)
	private String zipCode;

	@Column(name = "street_address", nullable = false)
	private String streetAddress;

	@Column(name = "lot_address", nullable = false)
	private String lotAddress;

	@Column(name = "detail_addres", nullable = false)
	private String detailAddress;

	@Column(name = "is_default", nullable = false)
	private byte isDefault;

	@Column(name = "is_deleted", nullable = false)
	private byte isDeleted;

	@Column(name = "order_history", nullable = false)
	private byte orderHistory;

	//    기본값 설정
	// @PrePersist
	// public void prePersist() {
	// 	this.isDefault = false;
	// 	this.isDeleted = false;
	// }
}
