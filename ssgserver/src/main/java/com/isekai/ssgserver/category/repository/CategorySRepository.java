package com.isekai.ssgserver.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.isekai.ssgserver.category.entity.CategoryS;

public interface CategorySRepository extends JpaRepository<CategoryS, Long> {
	List<CategoryS> findAllByCategoryMMediumName(String mediumName);

	@Query("SELECT cs.categorySId FROM CategoryS cs WHERE cs.smallName = :modifiedSmallName AND cs.categoryM.id = :categoryMId")
	Long findBySmallAndCategoryMId(@Param("modifiedSmallName") String modifiedSmallName,
		@Param("categoryMId") Long categoryMId);

	@Query("SELECT cs FROM CategoryS cs WHERE cs.categorySId = :categorySId")
	CategoryS findByCategorySId(@Param("categorySId") Long categorySId);
}
