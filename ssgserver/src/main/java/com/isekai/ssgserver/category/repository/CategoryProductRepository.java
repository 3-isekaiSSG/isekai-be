package com.isekai.ssgserver.category.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.category.entity.CategoryProduct;
import com.isekai.ssgserver.product.entity.Product;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {
	// 중분류 상품 조회
	@Query("SELECT cp.product FROM CategoryProduct cp WHERE (cp.categoryM.categoryMId = :categoryMId)")
	List<Product> findByCategoryMId(@Param("categoryMId") Long categoryMId);

	// 소분류 상품 조회
	@Query("SELECT cp.product FROM CategoryProduct cp WHERE (cp.categoryS.categorySId = :categorySId)")
	List<Product> findByCategorySId(@Param("categorySId") Long categorySId);
}


