package com.isekai.ssgserver.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.dto.CategoryMList;
import com.isekai.ssgserver.category.dto.CategoryMResponseDto;
import com.isekai.ssgserver.category.dto.CategoryResponseDto;
import com.isekai.ssgserver.category.dto.CategorySList;
import com.isekai.ssgserver.category.dto.CategorySResponseDto;
import com.isekai.ssgserver.category.entity.CategoryL;
import com.isekai.ssgserver.category.entity.CategoryM;
import com.isekai.ssgserver.category.entity.CategoryS;
import com.isekai.ssgserver.category.repository.CategoryLRepository;
import com.isekai.ssgserver.category.repository.CategoryMRepository;
import com.isekai.ssgserver.category.repository.CategorySRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {

	private final CategoryLRepository categoryLRepository;
	private final CategoryMRepository categoryMRepository;
	private final CategorySRepository categorySRepository;

	// 대,중분류
	public List<CategoryResponseDto> getCategory() {

		try {
			List<CategoryL> categoriesL = categoryLRepository.findAll();
			List<CategoryM> categoriesM = categoryMRepository.findAll();

			List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();

			AtomicInteger responseDtoId = new AtomicInteger(0);

			for (CategoryL cl : categoriesL) {
				Long categoryLId = cl.getCategoryLId();
				AtomicInteger categoryMListId = new AtomicInteger(0);

				List<CategoryM> categoryM = categoryMRepository.findAllByCategoryLCategoryLId(categoryLId);
				List<CategoryMList> categoryMLists = categoryM.stream().map(cm -> CategoryMList.builder()
						.id(categoryMListId.getAndIncrement())
						.categoryMId(cm.getCategoryMId())
						.mediumName(cm.getMediumName())
						.isColored(cm.getIsColored())
						.build())
					.collect(Collectors.toList());

				categoryResponseDtoList.add(CategoryResponseDto.builder()
					.id(responseDtoId.getAndIncrement())
					.categoryLId(categoryLId)
					.largeName(cl.getLargeName())
					.categoryMList(categoryMLists)
					.build()
				);
			}
			return categoryResponseDtoList;

		} catch (Exception exception) {
			throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
		}
	}

	// 중분류
	public CategoryMResponseDto getCategoryM(Long categoryLId) {

		try {

			List<CategoryM> categoryM = categoryMRepository.findAllByCategoryLCategoryLId(categoryLId);
			List<CategoryMList> categoryMLists = new ArrayList<>();

			AtomicInteger categoryListId = new AtomicInteger(0);

			categoryM.forEach(cm -> {
				categoryMLists.add(CategoryMList.builder()
					.id(categoryListId.getAndIncrement())
					.categoryMId(cm.getCategoryMId())
					.mediumName(cm.getMediumName())
					.isColored(cm.getIsColored())
					.build());
			});

			return CategoryMResponseDto.builder()
				.id(0)
				.categoryLId(categoryLId)
				.categoryMList(categoryMLists)
				.build();
		} catch (Exception exception) {
			throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
		}
	}

	// 소분류
	public CategorySResponseDto getCategoryS(Long categoryMId) {

		try {
			List<CategoryS> categoryS = categorySRepository.findAllByCategoryMCategoryMId(categoryMId);
			List<CategorySList> categorySLists = new ArrayList<>();

			AtomicInteger categoryListId = new AtomicInteger(0);

			categoryS.forEach(cs -> {
				categorySLists.add(CategorySList.builder()
					.id(categoryListId.getAndIncrement())
					.categorySId(cs.getCategorySId())
					.smallName(cs.getSmallName())
					.build());
			});

			return CategorySResponseDto.builder()
				.id(0)
				.categoryMId(categoryMId)
				.categorySList(categorySLists)
				.build();
		} catch (CustomException exception) {
			throw new CustomException(ErrorCode.NOT_FOUND_ENTITY);
		}
	}

}