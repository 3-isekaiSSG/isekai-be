package com.isekai.ssgserver.cart.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.cart.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {

	List<Cart> findByUuid(String uuid);

	List<Cart> findByCartValue(String cartValue);

	List<Cart> findByUuidOrderByCreatedAtDesc(String uuid);

	List<Cart> findByCartValueOrderByCreatedAtDesc(String cartValue);

	Integer countByUuid(String uuid);

	Integer countByCartValue(String cartValue);
}
