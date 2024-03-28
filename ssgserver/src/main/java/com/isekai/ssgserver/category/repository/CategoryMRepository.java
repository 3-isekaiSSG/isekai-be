package com.isekai.ssgserver.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isekai.ssgserver.category.entity.CategoryM;

public interface CategoryMRepository extends JpaRepository<CategoryM, Long> {
	Optional<List<CategoryM>> findAllByCategoryLLargeName(String largeName);
}
