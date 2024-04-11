package com.isekai.ssgserver.product.repository;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.ProductDocument;

@Repository
public interface ElasticSearchProductRepository extends ElasticsearchRepository<ProductDocument, Long> {

	List<ProductDocument> findByProductName(String productName);
}
