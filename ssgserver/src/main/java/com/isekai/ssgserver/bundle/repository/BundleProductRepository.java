package com.isekai.ssgserver.bundle.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.bundle.entity.BundleProduct;

@Repository
public interface BundleProductRepository extends JpaRepository<BundleProduct, Long> {
	@Query("SELECT bp.bundleProductId, bp.productCode, bp.bundle.bundleId FROM BundleProduct bp WHERE bp.bundle.bundleId = :bundleId")
	List<Object[]> findByBundleId(@Param("bundleId") Long bundleId);
}
