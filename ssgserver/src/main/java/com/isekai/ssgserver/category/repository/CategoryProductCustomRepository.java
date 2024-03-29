package com.isekai.ssgserver.category.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.product.entity.QDiscount;
import com.isekai.ssgserver.product.entity.QProduct;
import com.isekai.ssgserver.review.entity.QReviewScore;
import com.isekai.ssgserver.review.entity.QTotalPurchase;
import com.querydsl.core.types.OrderSpecifier;
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
	public Page<Product> findCategoryProduct(String largeName, String mediumName, String smallName, String criteria,
		String brandName, Integer minPrc, Integer maxPrc, Pageable pageable) {

		OrderSpecifier orderSpecifier = createOrderSpecifier(criteria);

		QProduct product = QProduct.product;
		QDiscount discount = QDiscount.discount;
		QReviewScore reviewScore = QReviewScore.reviewScore;
		QTotalPurchase totalPurchase = QTotalPurchase.totalPurchase;

		// List<Product> products = queryFactory

		return null;
	}

	// 정렬 로직
	public OrderSpecifier<?> createOrderSpecifier(String criteria) {

		QProduct product = QProduct.product;
		QDiscount discount = QDiscount.discount;
		QTotalPurchase totalPurchase = QTotalPurchase.totalPurchase;

		// 할인가가 존재하는지 체크하고 가격 정렬시 할인가 사용
		NumberExpression<Integer> effectivePrice = new CaseBuilder()
			.when(discount.discountPrice.isNotNull()).then(discount.discountPrice)
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
			default:
				return product.productId.asc();  // 임시방편
		}
	}
}
