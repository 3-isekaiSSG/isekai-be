package com.isekai.ssgserver.option.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.option.dto.OptionDepthDto;
import com.isekai.ssgserver.option.dto.OptionDetailDto;
import com.isekai.ssgserver.option.entity.Option;
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

	/**
	 * 상위 계층 선택에 따라, option list 구성해서 반환
	 * (ex/ 1단계 사이즈 "S" or "M" 선택 시 -> 2단계 색상 옵션 리스트 반환)
	 * @param productCode
	 * @param parentId 상위 계층 옵션의 options_id
	 * @return
	 */
	public List<OptionDetailDto> getOptionDetailByProductAndParentId(String productCode, Long parentId) {
		AtomicInteger id = new AtomicInteger(0);
		List<Option> optionList;
		if (parentId == null) {
			optionList = optionRepository.findAllByProductCodeAndDepth(productCode, 1);
		} else {
			optionList = optionRepository.findAllByProductCodeAndParentOptionsId(productCode, parentId);
		}

		return optionList.stream()
			.map(o -> OptionDetailDto.builder()
				.id(id.getAndIncrement())
				.optionsId(o.getOptionsId())
				.orderLimit(o.getOrderLimit())
				.stock(o.getStock())
				.value(o.getValue())
				.build())
			.toList();
	}
}
