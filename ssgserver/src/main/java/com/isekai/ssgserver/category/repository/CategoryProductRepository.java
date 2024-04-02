package com.isekai.ssgserver.category.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.category.entity.CategoryProduct;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Long> {

	@EntityGraph(attributePaths = {"categoryL", "categoryM", "categoryS"})
	Optional<CategoryProduct> findByProductCode(String productCode);

}


