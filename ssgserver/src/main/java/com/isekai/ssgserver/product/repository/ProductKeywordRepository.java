package com.isekai.ssgserver.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.ProductKeyword;

@Repository
public interface ProductKeywordRepository extends JpaRepository<ProductKeyword, Long> {

	List<ProductKeyword> findByNameContaining(String keyword);
}