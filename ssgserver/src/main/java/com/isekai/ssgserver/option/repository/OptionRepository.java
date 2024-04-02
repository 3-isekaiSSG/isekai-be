package com.isekai.ssgserver.option.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.isekai.ssgserver.option.dto.OptionDepthDto;
import com.isekai.ssgserver.option.entity.Option;

import io.lettuce.core.dynamic.annotation.Param;

public interface OptionRepository extends JpaRepository<Option, Long> {

	@Query("SELECT new com.isekai.ssgserver.option.dto.OptionDepthDto(o.depth, o.category) FROM Option o WHERE o.productCode = :productCode GROUP BY o.depth, o.category")
	List<OptionDepthDto> findCategoriesByProductCodeGroupedByDepth(@Param("productCode") String productCode);

	List<Option> findAllByProductCodeAndDepth(String productCode, int depth);

	List<Option> findAllByProductCodeAndParentOptionsId(String productCode, Long parentId);
}
