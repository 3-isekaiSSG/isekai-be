package com.isekai.ssgserver.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
