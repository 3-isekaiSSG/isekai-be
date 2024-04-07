package com.isekai.ssgserver.cart.entity;

import com.isekai.ssgserver.common.BaseEntity;
import com.isekai.ssgserver.option.entity.Option;

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
import lombok.Setter;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Table(name = "cart")
public class Cart extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "cart_id")
	private Long cartId;

	@Column(nullable = false)
	private String uuid;

	// 비회원 장바구니 쿠키 value
	@Column(name = "cart_value", nullable = true)
	private String cartValue;

	@Column(nullable = false)
	private int count;

	@Column(nullable = false)
	private byte checked;

	// 연관 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "options_id", nullable = false)
	private Option option;

	public void updateUuid(String uuid) {
		this.uuid = uuid;
	}
}
