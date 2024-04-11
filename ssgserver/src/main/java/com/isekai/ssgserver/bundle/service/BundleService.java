package com.isekai.ssgserver.bundle.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.bundle.dto.BundleCardResDto;
import com.isekai.ssgserver.bundle.dto.BundleInfoResDto;
import com.isekai.ssgserver.bundle.dto.BundleListResDto;
import com.isekai.ssgserver.bundle.entity.Bundle;
import com.isekai.ssgserver.bundle.repository.BundleRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BundleService {
	private final BundleRepository bundleRepository;

	@Transactional
	public Page<BundleListResDto> getBundleList(int page, int pageSize) {
		Pageable pageable = PageRequest.of(page, pageSize);

		Page<Object[]> bundlePage = bundleRepository.findBundleCode(page, pageable);

		return bundlePage.map(result -> {
			Long bundelId = (Long)result[0];
			String code = (String)result[1];
			return new BundleListResDto(bundelId, code);
		});
	}

	@Transactional
	public BundleCardResDto getBudleCardInfo(String code) {
		Bundle bundle = bundleRepository.findByCode(code)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		return BundleCardResDto.builder()
			.bundleId(bundle.getBundleId())
			.outerName(bundle.getOuterName())
			.code(bundle.getCode())
			.minPrice(bundle.getMinPrice())
			.build();
	}

	@Transactional
	public BundleInfoResDto getBundleDetails(String code) {
		Bundle bundel = bundleRepository.findByCode(code)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		return BundleInfoResDto.builder()
			.bundleId(bundel.getBundleId())
			.innerName(bundel.getInnerName())
			.code(bundel.getCode())
			.minPrice(bundel.getMinPrice())
			.reviewCount(bundel.getReviewCount())
			.avgScore(bundel.getAvgScore())
			.buyCount(bundel.getBuyCount())
			.build();
	}
}
