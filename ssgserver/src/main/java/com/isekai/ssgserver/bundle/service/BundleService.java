package com.isekai.ssgserver.bundle.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.isekai.ssgserver.bundle.dto.BundleCardResDto;
import com.isekai.ssgserver.bundle.dto.BundleInfoResDto;
import com.isekai.ssgserver.bundle.dto.BundleListResDto;
import com.isekai.ssgserver.bundle.dto.BundleProductListResDto;
import com.isekai.ssgserver.bundle.entity.Bundle;
import com.isekai.ssgserver.bundle.enums.BundleType;
import com.isekai.ssgserver.bundle.repository.BundleProductRepository;
import com.isekai.ssgserver.bundle.repository.BundleRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.product.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class BundleService {
	private final BundleRepository bundleRepository;
	private final BundleProductRepository bundleProductRepository;
	private final ProductRepository productRepository;

	@Transactional
	public Page<BundleListResDto> getBundleList(int page, int pageSize, BundleType sortType) {
		Pageable pageable;
		if (sortType == BundleType.LATEST) {
			pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
		} else if (sortType == BundleType.HIGHEST_RATING) {
			pageable = PageRequest.of(page, pageSize, Sort.by("avgScore").descending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("buy_count").descending());
		}

		Page<Object[]> bundlePage = bundleRepository.findBundleCode(pageable);

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

	@Transactional
	public List<BundleProductListResDto> getBundleProductList(String code) {
		Bundle bundle = bundleRepository.findByCode(code)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		Long bundleId = bundle.getBundleId();
		List<Object[]> bundleProductList = bundleProductRepository.findByBundleId(bundleId);

		System.out.println(bundleProductList);

		List<BundleProductListResDto> dtoList = bundleProductList.stream()
			.map(row -> BundleProductListResDto.builder()
				.bundleProductId((Long)row[0])
				.productCode((String)row[1])
				.bundleId((Long)row[2])
				.build())
			.collect(Collectors.toList());
		return dtoList;
	}
}
