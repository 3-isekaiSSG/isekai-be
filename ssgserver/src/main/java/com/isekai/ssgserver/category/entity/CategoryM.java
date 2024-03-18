package com.isekai.ssgserver.category.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CategoryM {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_m_id")
	private Long categoryMId;

	@Column(name = "medium_name")
	private String mediumName;

	@Column(name = "isSelected")
	private Boolean isSelected;

	// 연관 관계
	@ManyToOne
	@JoinColumn(name = "category_l_id")
	private CategoryL categoryL;

	@OneToMany(mappedBy = "categoryM")
	private List<CategoryS> categorySList = new ArrayList<>();

	@OneToMany(mappedBy = "categoryM")
	private List<CategoryProduct> categoryProductList = new ArrayList<>();

	@Builder
	public CategoryM(String mediumName, Boolean isSelected) {
		this.mediumName = mediumName;
		this.isSelected = isSelected;
	}
}
