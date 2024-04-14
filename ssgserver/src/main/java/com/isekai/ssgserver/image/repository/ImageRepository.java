package com.isekai.ssgserver.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.image.entity.Image;

import java.util.List;
import java.util.Optional;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	// List<Image> findAllByProduct(Product product);
	//
	Optional<Image> findByProductCodeAndIsThumbnail(String productCode, int isThumbnail);

	List<Image> findAllByProductCode(String productCode);
}
