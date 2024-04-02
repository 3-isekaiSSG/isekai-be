package com.isekai.ssgserver.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isekai.ssgserver.category.entity.CategoryL;

public interface CategoryLRepository extends JpaRepository<CategoryL, Long> {
	@Query("SELECT cl.categoryLId FROM CategoryL cl WHERE cl.largeName = :modifiedLargeName")
	Long findByLargeName(@Param("modifiedLargeName") String modifiedLargeName);
}
