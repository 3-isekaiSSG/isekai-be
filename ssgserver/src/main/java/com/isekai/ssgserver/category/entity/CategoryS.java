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
public class CategoryS {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_s_id")
	private Long categorySId;

	@Column(name = "small_name")
	private String smallName;

	// 연관 관계
	@ManyToOne
	@JoinColumn(name = "category_m_id")
	private CategoryM categoryM;

	@OneToMany(mappedBy = "categoryS")
	private List<CategoryProduct> categoryProductList = new ArrayList<>();

	@Builder
	public CategoryS(String smallName) {
		this.smallName = smallName;
	}
}
