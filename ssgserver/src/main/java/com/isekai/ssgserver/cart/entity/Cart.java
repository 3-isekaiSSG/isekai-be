package com.isekai.ssgserver.cart.entity;

import java.time.LocalDateTime;

import com.isekai.ssgserver.member.entity.Memeber;
import com.isekai.ssgserver.option.entity.Option;
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
@Table(name = "cart")
public class Cart {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private long cartId;

	@Column(nullable = false)
	private int count;

	@Column(nullable = false)
	private byte checked;

	@Column(name = "created_at", nullable = false)
	private LocalDateTime createdAt;

	// 연관 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Memeber memeber;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_id", nullable = false)
	private Option option;
}
