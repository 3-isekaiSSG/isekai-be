package com.isekai.ssgserver.option.entity;

import com.isekai.ssgserver.category.entity.CategoryL;
import com.isekai.ssgserver.category.entity.CategoryM;
import com.isekai.ssgserver.category.entity.CategoryS;
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
@Table(name = "option")
public class Option {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "option_id")
	private long optionId;

	@Column(nullable = false)
	private String value;

	@Column(nullable = false)
	private int stock;

	@Column(name = "order_limit")
	private int orderLimit;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "procut_id", nullable = false)
	private Product product;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_first_id")
	private CategoryL categoryL;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_second_id")
	private CategoryM categoryM;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "option_third_id")
	private CategoryS categoryS;

}
