package com.isekai.ssgserver.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.category.dto.CategoryMList;
import com.isekai.ssgserver.category.dto.CategoryResponseDto;
import com.isekai.ssgserver.category.entity.CategoryL;
import com.isekai.ssgserver.category.entity.CategoryM;
import com.isekai.ssgserver.category.repository.CategoryLRepository;
import com.isekai.ssgserver.category.repository.CategoryMRepository;
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

	// public CategoryService(CategoryLRepository categoryLRepository, CategoryMRepository categoryMRepository) {
	// 	this.categoryLRepository = categoryLRepository;
	// 	this.categoryMRepository = categoryMRepository;
	// }

	// 대,중분류
	public List<CategoryResponseDto> getCategory() {

		try {
			List<CategoryL> categoriesL = categoryLRepository.findAll();
			List<CategoryM> categoriesM = categoryMRepository.findAll();

			List<CategoryResponseDto> categoryResponseDtoList = new ArrayList<>();
			List<CategoryMList> categoryMLists;

			int responseDtoId = 0;

			for (CategoryL cl : categoriesL) {
				categoryMLists = new ArrayList<>();
				int categoryMListId = 0;

				for (CategoryM cm : categoriesM) {
					if (Objects.equals(cm.getCategoryL().getCategoryLId(), cl.getCategoryLId())) {
						categoryMLists.add(CategoryMList.builder()
							.id((long)categoryMListId++)
							.categoryMId(cm.getCategoryMId())
							.mediumName(cm.getMediumName())
							.isColored(cm.getIsColored())
							.build());
					}
				}
				categoryResponseDtoList.add(CategoryResponseDto.builder()
					.id((long)responseDtoId++)
					.categoryLId(cl.getCategoryLId())
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

}
