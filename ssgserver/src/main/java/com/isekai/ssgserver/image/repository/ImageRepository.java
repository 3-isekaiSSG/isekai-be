package com.isekai.ssgserver.image.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.image.entity.Image;
import com.isekai.ssgserver.product.entity.Product;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	List<Image> findAllByProduct(Product product);

}
