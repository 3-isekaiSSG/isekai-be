package com.isekai.ssgserver.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.dto.CategoryLResponseDto;
import com.isekai.ssgserver.category.dto.CategoryMList;
import com.isekai.ssgserver.category.dto.CategoryMResponseDto;
import com.isekai.ssgserver.category.dto.CategorySList;
import com.isekai.ssgserver.category.dto.CategorySResponseDto;
import com.isekai.ssgserver.category.entity.CategoryL;
import com.isekai.ssgserver.category.entity.CategoryM;
import com.isekai.ssgserver.category.entity.CategoryS;
import com.isekai.ssgserver.category.repository.CategoryLRepository;
import com.isekai.ssgserver.category.repository.CategoryMRepository;
import com.isekai.ssgserver.category.repository.CategorySRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
@Service
public class CategoryService {

	private final CategoryLRepository categoryLRepository;
	private final CategoryMRepository categoryMRepository;
	private final CategorySRepository categorySRepository;

	// 대분류
	public List<CategoryLResponseDto> getCategoryL() {

		List<CategoryL> categoriesL = categoryLRepository.findAll();
		AtomicInteger responseId = new AtomicInteger();

		// "전체" 항목을 포함하는 CategoryLResponseDto 객체를 생성
		CategoryLResponseDto whole = CategoryLResponseDto.builder()
			.id(responseId.getAndIncrement())
			.categoryId(0L)
			.name("전체")
			.img("")
			.build();

		// 스트림 처리를 통해 기존 리스트에 "전체" 항목을 추가
		List<CategoryLResponseDto> categoryLResponse = Stream.concat(Stream.of(whole),
				categoriesL.stream().map(cl -> CategoryLResponseDto.builder()
					.id(responseId.getAndIncrement())
					.categoryId(cl.getCategoryLId())
					.name(cl.getLargeName())
					.img(cl.getLargeImg())
					.build()))
			.toList();

		return categoryLResponse;

	}

	// 중분류
	public CategoryMResponseDto getCategoryM(String largeName) {

		List<CategoryM> categoryM = categoryMRepository.findAllByCategoryLLargeName(largeName);
		List<CategoryMList> categoryMLists = new ArrayList<>();

		AtomicInteger categoryListId = new AtomicInteger(0);

		categoryM.forEach(cm -> {
			categoryMLists.add(CategoryMList.builder()
				.id(categoryListId.getAndIncrement())
				.categoryId(cm.getCategoryMId())
				.name(cm.getMediumName())
				.isColored(cm.getIsColored())
				.img(cm.getMediumImg())
				.build());
		});

		return CategoryMResponseDto.builder()
			.id(0)
			.largeName(largeName)
			.categoryMList(categoryMLists)
			.build();

	}

	// 소분류
	public CategorySResponseDto getCategoryS(String mediumName) {

		List<CategoryS> categoryS = categorySRepository.findAllByCategoryMMediumName(mediumName);
		List<CategorySList> categorySLists = new ArrayList<>();

		AtomicInteger categoryListId = new AtomicInteger(0);

		categoryS.forEach(cs -> {
			categorySLists.add(CategorySList.builder()
				.id(categoryListId.getAndIncrement())
				.categoryId(cs.getCategorySId())
				.name(cs.getSmallName())
				.build());
		});

		return CategorySResponseDto.builder()
			.id(0)
			.mediumName(mediumName)
			.categorySList(categorySLists)
			.build();

	}

}
