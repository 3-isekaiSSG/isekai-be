package com.isekai.ssgserver.image.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.image.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	// List<Image> findAllByProduct(Product product);
	//
	// Optional<Image> findByProductAndIsThumbnail(Product product, int isThumbnail);

}
