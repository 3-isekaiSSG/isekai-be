package com.isekai.ssgserver.review.entity;

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
@Table(name = "total_purchase")
public class TotalPurchase {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "total_purchase_id")
	private Long totalPurchaseId;

	@Column(name = "count", nullable = false)
	private int count;

	@Column(name = "cur_rank")
	private Long curRank;

	@Column(name = "pre_rank")
	private Long preRank;

	@Column(name = "product_code")
	private String productCode;

}
