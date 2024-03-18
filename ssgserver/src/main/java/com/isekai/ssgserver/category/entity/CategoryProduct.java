package com.isekai.ssgserver.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryProduct {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_product")
	private Long categoryProductId;

	// 연관 관계
	// @ManyToOne
	// @JoinColumn(name = "product_id")
	// private Product product;

	@ManyToOne
	@JoinColumn(name = "category_l_id")
	private CategoryL categoryL;

	@ManyToOne
	@JoinColumn(name = "category_m_id")
	private CategoryM categoryM;

	@ManyToOne
	@JoinColumn(name = "category_s_id")
	private CategoryS categoryS;
}
