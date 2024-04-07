package com.isekai.ssgserver.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isekai.ssgserver.category.entity.CategoryS;

public interface CategorySRepository extends JpaRepository<CategoryS, Long> {
	List<CategoryS> findAllByCategoryMMediumName(String mediumName);

	@Query("SELECT cs.categorySId FROM CategoryS cs WHERE cs.smallName = :modifiedSmallName")
	Long findBySmallName(String modifiedSmallName);
}
