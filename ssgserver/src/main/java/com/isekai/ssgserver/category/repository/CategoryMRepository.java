package com.isekai.ssgserver.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isekai.ssgserver.category.entity.CategoryM;

public interface CategoryMRepository extends JpaRepository<CategoryM, Long> {
	List<CategoryM> findAllByCategoryLLargeName(String largeName);

	@Query("SELECT cm.categoryMId FROM CategoryM cm WHERE cm.mediumName = :modifiedMediumName")
	Long findByMediumName(@Param("modifiedMediumName") String modifiedMediumName);

	@Query("SELECT cm FROM CategoryM cm WHERE cm.categoryMId = :categoryMId")
	CategoryM findByCategoryMId(@Param("categoryMId") Long categoryMId);
}
