package com.isekai.ssgserver.seller.controller;

import com.isekai.ssgserver.delivery.dto.DeliveryTypeDto;
import com.isekai.ssgserver.seller.dto.SellerDto;
import com.isekai.ssgserver.seller.service.SellerService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/sellers")
@Tag(name = "Seller", description = "판매자 조회 API document")
public class SellerController {

    private final SellerService sellerService;

    @GetMapping("/products/{productCode}")
    @Operation(summary = "판매자 조회 - 단일 상품", description = "상품코드로 판매자 조회")
    public ResponseEntity<SellerDto> getSellerByProduct(@PathVariable String productCode) {
        SellerDto sellerDto = sellerService.getSellerByProduct(productCode);
        return ResponseEntity.ok(sellerDto);
    }
}
