package com.isekai.ssgserver.bundle.service;

import java.util.ArrayList;
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
	public List<BundleListResDto> getBundleList(int page, int pageSize, BundleType sortType) {
		Pageable pageable;
		if (sortType == BundleType.LATEST) {
			pageable = PageRequest.of(page, pageSize, Sort.by("createdAt").descending());
		} else if (sortType == BundleType.HIGHEST_RATING) {
			pageable = PageRequest.of(page, pageSize, Sort.by("avgScore").descending());
		} else {
			pageable = PageRequest.of(page, pageSize, Sort.by("buyCount").descending());
		}
		List<BundleListResDto> bundleList = new ArrayList<>();
		for (int i = 0; i < pageSize; i++) {
			bundleList.add(new BundleListResDto((long)i, null, null));
		}

		Page<Object[]> bundlePage = bundleRepository.findBundleCode(pageable);
		int index = 0;
		for (Object[] result : bundlePage.getContent()) {
			Long bundleId = (Long)result[0];
			String code = (String)result[1];
			bundleList.get(index).setBundleId(bundleId);
			bundleList.get(index).setCode(code);
			index++;
		}
		return bundleList;
	}

	@Transactional
	public BundleCardResDto getBundleCardInfo(String code) {
		Bundle bundle = bundleRepository.findByCode(code)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		return BundleCardResDto.builder()
			.bundleId(bundle.getBundleId())
			.outerName(bundle.getOuterName())
			.code(bundle.getCode())
			.minPrice(bundle.getMinPrice())
			.buyCount(bundle.getBuyCount())
			.imgUrl(bundle.getImageUrl())
			.build();
	}

	@Transactional
	public BundleInfoResDto getBundleDetails(String code) {
		Bundle bundle = bundleRepository.findByCode(code)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		return BundleInfoResDto.builder()
			.bundleId(bundle.getBundleId())
			.innerName(bundle.getInnerName())
			.code(bundle.getCode())
			.minPrice(bundle.getMinPrice())
			.reviewCount(bundle.getReviewCount())
			.avgScore(bundle.getAvgScore())
			.buyCount(bundle.getBuyCount())
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
