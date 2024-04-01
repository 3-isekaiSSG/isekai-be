package com.isekai.ssgserver.seller.repository;

import static com.isekai.ssgserver.category.entity.QCategoryProduct.*;

import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.category.entity.QCategoryProduct;
import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.product.entity.QProduct;
import com.isekai.ssgserver.seller.dto.BrandNameResponseDto;
import com.isekai.ssgserver.seller.entity.QSellerProduct;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class BrandNameCustomRepository extends QuerydslRepositorySupport {

	private JPAQueryFactory queryFactory;

	public BrandNameCustomRepository(JPAQueryFactory queryFactory) {
		super(Product.class);
		this.queryFactory = queryFactory;
	}

	public List<BrandNameResponseDto> findBrandProductCountByCategory(String largeName, String mediumName,
		String smallName) {

		QProduct product = QProduct.product;
		QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
		QSellerProduct sellerProduct = QSellerProduct.sellerProduct;

		List<BrandNameResponseDto> results = queryFactory
			.select(Projections.fields(BrandNameResponseDto.class,
				sellerProduct.seller.name.as("name"),
				product.count().as("cnt")))
			.from(product)
			.leftJoin(sellerProduct).on(product.code.eq(sellerProduct.productCode))
			.leftJoin(categoryProduct).on(product.code.eq(categoryProduct.productCode))
			.where(
				largeNameEq(largeName),
				mediumNameEq(mediumName),
				smallNameEq(smallName)
			)
			.groupBy(sellerProduct.seller.name)
			.fetch();

		return results;
	}

	private BooleanExpression largeNameEq(String largeName) {
		return largeName == null ? null : categoryProduct.categoryL.largeName.eq(largeName);
	}

	private BooleanExpression mediumNameEq(String mediumName) {
		return mediumName == null ? null : categoryProduct.categoryM.mediumName.eq(mediumName);
	}

	private BooleanExpression smallNameEq(String smallName) {
		return smallName == null ? null : categoryProduct.categoryS.smallName.eq(smallName);
	}
}
