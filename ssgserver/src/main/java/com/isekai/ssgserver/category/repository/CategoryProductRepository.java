package com.isekai.ssgserver.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.category.entity.CategoryProduct;
import com.isekai.ssgserver.product.entity.Product;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {
	// 중분류 상품 조회
	// @Query("SELECT cp.product FROM CategoryProduct cp WHERE (cp.categoryM.categoryMId = :categoryMId)")
	// Page<Product> findByCategoryMId(@Param("categoryMId") Long categoryMId, Pageable pageable);
	// @Query("SELECT cp.product FROM CategoryProduct cp WHERE (cp.categoryM.mediumName = :mediumName)")
	// Page<Product> findByCategoryMName(@Param("mediumName") String mediumName, Pageable pageable);

	// 소분류 상품 조회
	// @Query("SELECT cp.product FROM CategoryProduct cp WHERE (cp.categoryS.categorySId = :categorySId)")
	// List<Product> findByCategorySId(@Param("categorySId") Long categorySId);
	@Query("SELECT cp.product FROM CategoryProduct cp WHERE cp.categoryS.smallName = :smallName")
	Page<Product> findByCategorySName(@Param("smallName") String smallName, Pageable pageable);
}


