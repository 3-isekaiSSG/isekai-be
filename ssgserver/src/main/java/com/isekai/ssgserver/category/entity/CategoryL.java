package com.isekai.ssgserver.category.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryL {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_l_id")
	private Long categoryLId;

	@Column(name = "name")
	private String name;

	// 연관 관계
	@OneToMany(mappedBy = "categoryL")
	private List<CategoryM> categoryMList = new ArrayList<>();

	@Builder
	public CategoryL(Long categoryLId, String name) {
		this.categoryLId = categoryLId;
		this.name = name;
	}
}
