package com.isekai.ssgserver.image.controller;

import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.image.dto.ImageDto;
import com.isekai.ssgserver.image.service.ImageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
@Tag(name = "Image", description = "이미지 조회 API document")
public class ImageController {

    private final ImageService imageService;

    @GetMapping("/products/{productCode}/thumbnail")
    @Operation(summary = "썸네일(대표 이미지) 조회 - 단일 상품", description = "상품 외부에 표시되는 대표 사진 1장을 조회합니다.")
    public ResponseEntity<ImageDto> getThumbnailImageByProduct(@PathVariable String productCode) {
        ImageDto imageDto = imageService.getThumbnailImageByProduct(productCode);
        return ResponseEntity.ok(imageDto);
    }

    @GetMapping("/products/{productCode}")
    @Operation(summary = "상품 이미지 리스트 조회 - 단일 상품", description = "상품 내부에 표시되는 모든 사진 리스트를 조회합니다.")
    public ResponseEntity<List<ImageDto>> getImagesByProduct(@PathVariable String productCode) {
        List<ImageDto> imageDtoList = imageService.getImagesByProduct(productCode);
        return ResponseEntity.ok(imageDtoList);
    }

}
