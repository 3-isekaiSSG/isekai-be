package com.isekai.ssgserver.order.repository;

import static com.isekai.ssgserver.delivery.entity.QDelivery.*;
import static com.isekai.ssgserver.order.entity.QOrder.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.delivery.entity.QDelivery;
import com.isekai.ssgserver.order.entity.Order;
import com.isekai.ssgserver.order.entity.QOrder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

@Repository
public class OrderCustomRepository extends QuerydslRepositorySupport {

	private final JPAQueryFactory queryFactory;

	public OrderCustomRepository(JPAQueryFactory queryFactory) {

		super(Order.class);
		this.queryFactory = queryFactory;
	}

	public List<Order> findByUuidAndFilter(String uuid, Integer month, LocalDate endDate, byte dType) {

		QOrder order = QOrder.order;
		QDelivery delivery = QDelivery.delivery;

		return queryFactory
			.selectFrom(order)
			.leftJoin(delivery).on(order.ordersId.eq(delivery.order.ordersId))
			.where(uuidEq(uuid), deliveryTypeEq(dType), dateBetween(month, endDate))
			.orderBy(order.createdAt.desc())
			.fetch();

	}

	private BooleanExpression uuidEq(String uuid) {

		return order.uuid.eq(uuid);
	}

	private BooleanExpression deliveryTypeEq(byte dType) {

		return delivery.deliveryType.eq(dType);
	}

	private BooleanExpression dateBetween(Integer month, LocalDate endDate) {

		LocalDate startDate;

		if (month == null && endDate == null) {
			endDate = LocalDate.now();
			startDate = endDate.minusMonths(3);
			return order.createdAt.between(startDate.atStartOfDay(), endDate.atStartOfDay()); // 혹은 기본값 혹은 예외 처리
		}

		endDate = LocalDate.now();
		startDate = endDate.minusMonths(month);
		return order.createdAt.between(startDate.atStartOfDay(), endDate.atStartOfDay());
	}
}
