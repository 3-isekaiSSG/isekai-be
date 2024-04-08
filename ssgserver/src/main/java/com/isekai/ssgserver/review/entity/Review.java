package com.isekai.ssgserver.review.entity;

import com.isekai.ssgserver.common.BaseEntity;
import com.isekai.ssgserver.order.entity.OrderProduct;

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
@Table(name = "review")
public class Review extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "review_id")
	private Long rewiewId;

	@Column(nullable = false)
	private String uuid;

	@Column(name = "account_id", nullable = false)
	private String accountId;

	@Column(name = "score", nullable = false)
	private byte score;

	@Column(name = "review_content", nullable = false)
	private String reviewContent;

	@Column(name = "review_image")
	private String reviewImage;

	@Column(name = "product_id")
	private Long productId;

	// 연관 관계
	@ManyToOne(fetch = FetchType.LAZY, optional = true) // 추후 빼기 테스트용
	@JoinColumn(name = "order_product_id", nullable = false)
	private OrderProduct orderProduct;
}
