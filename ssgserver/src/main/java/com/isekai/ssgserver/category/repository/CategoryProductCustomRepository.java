package com.isekai.ssgserver.category.repository;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.Product;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CategoryProductCustomRepository extends QuerydslRepositorySupport {

	private final JPAQueryFactory queryFactory;

	public CategoryProductCustomRepository(JPAQueryFactory queryFactory) {
		super(Product.class);
		this.queryFactory = queryFactory;
	}

	// 중분류 상품 조회
	// public Page<Product> findByCategoryMName(String mediumName, Pageable pageable) {
	// 	QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
	// 	QProduct product = QProduct.product;
	//
	// 	List<Product> products = queryFactory
	// 		.select(categoryProduct.product)
	// 		.from(categoryProduct)
	// 		.where(categoryProduct.categoryM.mediumName.eq(mediumName))
	// 		.offset(pageable.getOffset())
	// 		.limit(pageable.getPageSize())
	// 		.fetch();
	//
	// 	long total = queryFactory
	// 		.select(categoryProduct.product.count())
	// 		.from(categoryProduct)
	// 		.where(categoryProduct.categoryM.mediumName.eq(mediumName))
	// 		.fetchOne();
	//
	// 	return new PageImpl<>(products, pageable, total);
	// }
}
