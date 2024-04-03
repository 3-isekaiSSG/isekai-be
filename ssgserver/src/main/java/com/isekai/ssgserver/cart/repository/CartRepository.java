package com.isekai.ssgserver.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.cart.dto.CartInfoDto;
import com.isekai.ssgserver.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	@Query("SELECT new com.isekai.ssgserver.cart.dto.CartInfoDto(o.productCode, c.count, c.checked) " +
		"FROM Cart c " +
		"JOIN c.option o " +
		"JOIN ProductDeliveryType pdt ON o.productCode = pdt.productCode " +
		"JOIN pdt.deliveryType dt " +
		"WHERE c.uuid = :uuid AND dt.deliveryTypeId = :deliveryTypeId " +
		"ORDER BY c.createdAt")
	List<CartInfoDto> findCartByUuidAndDeliveryType(String uuid, byte deliveryTypeId);

}
