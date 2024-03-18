package com.isekai.ssgserver.category.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
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
@Table(name = "category_M")
public class CategoryM {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_m_id")
	private long categoryMId;

	@Column(name = "medium_name",nullable = false)
	private String mediumName;

	@Column(name = "isSelected",nullable = false)
	private Boolean isSelected;

	// 연관 관계
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_l_id",nullable = false)
	private CategoryL categoryL;

	// @OneToMany(mappedBy = "categoryM")
	// private List<CategoryS> categorySList = new ArrayList<>();
	//
	// @OneToMany(mappedBy = "categoryM")
	// private List<CategoryProduct> categoryProductList = new ArrayList<>();

	// @Builder
	// public CategoryM(String mediumName, Boolean isSelected) {
	// 	this.mediumName = mediumName;
	// 	this.isSelected = isSelected;
	// }
}
