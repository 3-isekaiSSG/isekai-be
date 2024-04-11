package com.isekai.ssgserver.product.service;

import java.util.List;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.isekai.ssgserver.product.entity.ProductDocument;
import com.isekai.ssgserver.product.repository.ElasticSearchProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductSearchService {

	private final RestHighLevelClient restHighLevelClient;
	private final ElasticSearchProductRepository elasticSearchProductRepository;

	public List<ProductDocument> searchProduct(String productName) {

		List<ProductDocument> productList;

		productList = elasticSearchProductRepository.findByProductName(productName);

		return productList;
	}
}
