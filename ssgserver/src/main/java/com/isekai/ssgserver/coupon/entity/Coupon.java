package com.isekai.ssgserver.coupon.entity;

import java.security.Timestamp;
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
@Table(name = "coupon")
public class Coupon {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "coupon_id")
	private long couponId;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "discount_rate")
	private byte discountRate;

	@Column(name = "start_date", nullable = false)
	private LocalDateTime startDate;

	@Column(name = "expire_date", nullable = false)
	private Timestamp expireDate;

}
