package com.isekai.ssgserver.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.ProductKeyword;

@Repository
public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Long> {

	@Query("SELECT DISTINCT p.productCode FROM ProductKeyword p WHERE p.name LIKE %:keyword%")
	List<String> findDistinctProductCodeByNameContaining(String keyword);
}
