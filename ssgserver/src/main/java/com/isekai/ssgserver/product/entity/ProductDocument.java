package com.isekai.ssgserver.product.entity;

import java.time.LocalDateTime;

import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Document(indexName = "product")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Mapping(mappingPath = "elastic/product-mapping.json")
@Setting(settingPath = "elastic/product-setting.json")
@ToString
public class ProductDocument {

	@Id
	private Long id;

	@Field(type = FieldType.Text, analyzer = "nori")
	private String productName;

	@Field(type = FieldType.Integer)
	private Integer price;

	@Field(type = FieldType.Text)
	private String code;

	@Field(type = FieldType.Text)
	private String productDetail;

	@Field(type = FieldType.Integer)
	private Integer adultSales;

	@Field(type = FieldType.Integer)
	private Integer status;

	@Field(type = FieldType.Date, format = DateFormat.basic_date)
	private LocalDateTime createdAt;

	@Builder
	public ProductDocument(Long id, String productName, Integer price, String code, String productDetail,
		Integer adultSales, Integer status, LocalDateTime createdAt) {

		this.id = id;
		this.productName = productName;
		this.price = price;
		this.code = code;
		this.productDetail = productDetail;
		this.adultSales = adultSales;
		this.status = status;
		this.createdAt = createdAt;
	}

}
