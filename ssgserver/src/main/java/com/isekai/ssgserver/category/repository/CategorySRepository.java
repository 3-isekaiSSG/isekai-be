package com.isekai.ssgserver.category.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isekai.ssgserver.category.entity.CategoryS;

public interface CategorySRepository extends JpaRepository<CategoryS, Long> {
	Optional<List<CategoryS>> findAllByCategoryMMediumName(String mediumName);
}
