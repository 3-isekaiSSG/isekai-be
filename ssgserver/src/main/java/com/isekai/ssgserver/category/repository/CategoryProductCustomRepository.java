package com.isekai.ssgserver.category.repository;

import static com.isekai.ssgserver.category.entity.QCategoryProduct.*;
import static com.isekai.ssgserver.delivery.entity.QProductDeliveryType.*;
import static com.isekai.ssgserver.product.entity.QDiscount.*;
import static com.isekai.ssgserver.product.entity.QProduct.*;
import static com.isekai.ssgserver.review.entity.QReviewScore.*;
import static com.isekai.ssgserver.review.entity.QTotalPurchase.*;
import static com.isekai.ssgserver.seller.entity.QSellerProduct.*;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.category.entity.QCategoryProduct;
import com.isekai.ssgserver.delivery.entity.QProductDeliveryType;
import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.product.entity.QDiscount;
import com.isekai.ssgserver.product.entity.QProduct;
import com.isekai.ssgserver.review.entity.QReviewScore;
import com.isekai.ssgserver.review.entity.QTotalPurchase;
import com.isekai.ssgserver.seller.entity.QSellerProduct;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class CategoryProductCustomRepository extends QuerydslRepositorySupport {

	private final JPAQueryFactory queryFactory;

	public CategoryProductCustomRepository(JPAQueryFactory queryFactory) {
		super(Product.class);
		this.queryFactory = queryFactory;
	}

	// 카테고리 상품 조회
	public Page<Product> findCategoryProduct(@Param("largeName") String largeName,
		@Param("mediumName") String mediumName, @Param("smallName") String smallName,
		@Param("sort") String criteria, @Param("brandName") String brandName, @Param("dType") String dType,
		@Param("minPrc") Integer minPrc, @Param("maxPrc") Integer maxPrc, Pageable pageable) {

		if (criteria == null) {
			criteria = "id";
		}

		OrderSpecifier orderSpecifier = createOrderSpecifier(criteria);

		QProduct product = QProduct.product;
		QCategoryProduct categoryProduct = QCategoryProduct.categoryProduct;
		QDiscount discount = QDiscount.discount;
		QReviewScore reviewScore = QReviewScore.reviewScore;
		QTotalPurchase totalPurchase = QTotalPurchase.totalPurchase;
		QSellerProduct sellerProduct = QSellerProduct.sellerProduct;
		QProductDeliveryType productDeliveryType = QProductDeliveryType.productDeliveryType;

		List<Product> products = queryFactory
			.selectFrom(product)
			.leftJoin(categoryProduct).on(product.code.eq(categoryProduct.productCode))
			.leftJoin(sellerProduct).on(product.code.eq(sellerProduct.productCode))
			.leftJoin(productDeliveryType).on(product.code.eq(productDeliveryType.productCode))
			.leftJoin(discount).on(product.code.eq(discount.productCode))
			.leftJoin(reviewScore).on(product.code.eq(reviewScore.productCode))
			.leftJoin(totalPurchase).on(product.code.eq(totalPurchase.productCode))
			.where(
				largeNameEq(largeName),
				mediumNameEq(mediumName),
				smallNameEq(smallName),
				brandNameEq(brandName),
				dTypeEq(dType),
				priceBetween(minPrc, maxPrc)
			)
			.orderBy(orderSpecifier)
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		// 총 개수 조회
		long total = queryFactory
			.selectFrom(product)
			.leftJoin(categoryProduct).on(product.code.eq(categoryProduct.productCode))
			.leftJoin(sellerProduct).on(product.code.eq(sellerProduct.productCode))
			.leftJoin(productDeliveryType).on(product.code.eq(productDeliveryType.productCode))
			.leftJoin(discount).on(product.code.eq(discount.productCode))
			.where(
				largeNameEq(largeName),
				mediumNameEq(mediumName),
				smallNameEq(smallName),
				brandNameEq(brandName),
				dTypeEq(dType),
				priceBetween(minPrc, maxPrc)
			)
			.fetchCount();

		return new PageImpl<>(products, pageable, total);
	}

	// 정렬 로직
	private OrderSpecifier<?> createOrderSpecifier(String criteria) {

		// 할인가가 존재하는지 체크하고 가격 정렬시 할인가 사용
		NumberExpression<Integer> effectivePrice = new CaseBuilder()
			.when(discount.discountRate.gt(0)).then(discount.discountPrice)
			.otherwise(product.price);

		switch (criteria) {
			case "prcasc":  // 가격낮은순
				return effectivePrice.asc();
			case "prcdsc":  // 가격높은순
				return effectivePrice.desc();
			case "sale":  // 판매순
				return totalPurchase.count.desc();
			case "dcrt":  // 할인율순
				return discount.discountRate.desc();
			case "regdt":  // 신상품순
				return product.createdAt.desc();
			case "cnt":  // 리뷰많은순
				return reviewScore.reviewCount.desc();
			default:
				return product.productId.asc();  // 임시방편
		}
	}

	// 필터링 조건
	private BooleanExpression largeNameEq(String largeName) {
		return largeName == null ? null : categoryProduct.categoryL.largeName.eq(largeName);
	}

	private BooleanExpression mediumNameEq(String mediumName) {
		return mediumName == null ? null : categoryProduct.categoryM.mediumName.eq(mediumName);
	}

	private BooleanExpression smallNameEq(String smallName) {
		return smallName == null ? null : categoryProduct.categoryS.smallName.eq(smallName);
	}

	private BooleanExpression brandNameEq(String brandName) {
		return brandName == null ? null : sellerProduct.seller.name.eq(brandName);
	}

	private BooleanExpression dTypeEq(String dType) {
		return dType == null ? null : productDeliveryType.deliveryType.name.eq(dType);
	}

	private BooleanExpression priceBetween(Integer minPrc, Integer maxPrc) {

		// 할인가가 존재하는지 체크하고 가격 정렬시 할인가 사용
		NumberExpression<Integer> effectivePrice = new CaseBuilder()
			.when(discount.discountRate.gt(0)).then(discount.discountPrice)
			.otherwise(product.price);

		if (minPrc != null && maxPrc != null) {
			return effectivePrice.between(minPrc, maxPrc);
		} else if (minPrc != null) {
			return effectivePrice.goe(minPrc);
		} else if (maxPrc != null) {
			return effectivePrice.loe(maxPrc);
		} else {
			return null;
		}
	}
}
