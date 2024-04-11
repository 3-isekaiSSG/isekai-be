package com.isekai.ssgserver.bundle.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.bundle.entity.Bundle;

@Repository
public interface BundleRepository extends JpaRepository<Bundle, Long> {

	@Query("SELECT b.bundleId, b.code FROM Bundle b")
	Page<Object[]> findBundleCode(Pageable pageSize);

	Optional<Bundle> findByCode(String code);
}
