package com.isekai.ssgserver.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

	@Column(name = "name")
	private String name;

	// 연관 관계
	@ManyToOne
	@JoinColumn(name = "category_l_id")
	private CategoryL categoryL;

	@Builder
	public CategoryM(Long categoryMId, String name) {
		
	}
}
