package com.isekai.ssgserver.option.entity;

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
@Table(name = "options")
public class Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "options_id")
	private Long optionsId;

	@Column(name = "value")
	private String value;

	@Column(name = "stock", nullable = false)
	private int stock;

	@Column(name = "orders_limit")
	private int ordersLimit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "product_id", nullable = false)
	private Product product;

	@Column(name = "option_first_id")
	private Long optionFirstId;

	@Column(name = "option_second_id")
	private Long optionSecondId;

	@Column(name = "option_third_id")
	private Long optionThirdId;

}
