package com.isekai.ssgserver.category.entity;

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
@Table(name = "category_s")
public class CategoryS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_s_id")
	private Long categorySId;

	@Column(name = "small_name", nullable = false)
	private String smallName;

	// 연관 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_m_id", nullable = false)
	private CategoryM categoryM;

	// @OneToMany(mappedBy = "categoryS")
	// private List<CategoryProduct> categoryProductList = new ArrayList<>();
	//
	// @Builder
	// public CategoryS(String smallName) {
	// 	this.smallName = smallName;
	// }
}
