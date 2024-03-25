package com.isekai.ssgserver.seller.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.product.entity.Product;
import com.isekai.ssgserver.seller.entity.Seller;
import com.isekai.ssgserver.seller.entity.SellerProduct;

@Repository
public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {

	@Query("SELECT sp.seller FROM SellerProduct sp WHERE(sp.product.productId = :productId)")
	List<Seller> findByProductId(Long productId);

	SellerProduct findByProduct(Product product);
}
