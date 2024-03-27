package com.isekai.ssgserver.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isekai.ssgserver.category.entity.CategoryM;

public interface CategoryMRepository extends JpaRepository<CategoryM, Long> {
	List<CategoryM> findAllByCategoryLLargeName(String largeName);
}
