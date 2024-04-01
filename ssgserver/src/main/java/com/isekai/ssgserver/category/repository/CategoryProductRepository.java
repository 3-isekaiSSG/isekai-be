package com.isekai.ssgserver.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.category.entity.CategoryProduct;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {
}


