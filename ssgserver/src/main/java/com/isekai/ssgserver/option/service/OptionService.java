package com.isekai.ssgserver.option.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.option.dto.OptionDepthDto;
import com.isekai.ssgserver.option.repository.OptionRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class OptionService {

	private final OptionRepository optionRepository;

	/**
	 * 상품에 속해있는 option의 category 값만 모두 추출
	 * @param productCode
	 * @return
	 */
	public List<OptionDepthDto> getAllOptionCategoryByProduct(String productCode) {

		return optionRepository.findCategoriesByProductCodeGroupedByDepth(productCode);
	}
}
