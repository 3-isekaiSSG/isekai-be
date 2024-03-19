package com.isekai.ssgserver.category.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "category_L")
public class CategoryL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_l_id")
	private long categoryLId;

	@Column(name = "large_name", nullable = false)
	private String largeName;

	// 연관 관계
	// @OneToMany(mappedBy = "categoryL")
	// private List<CategoryM> categoryMList = new ArrayList<>();
	//
	// @OneToMany(mappedBy = "categoryL")
	// private List<CategoryProduct> categoryProductList = new ArrayList<>();

	// @Builder
	// public CategoryL(String name) {
	// 	this.name = name;
	// }
}
