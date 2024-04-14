package com.isekai.ssgserver.seller.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.seller.entity.SellerProduct;

import java.util.List;
import java.util.Optional;

@Repository
public interface SellerProductRepository extends JpaRepository<SellerProduct, Long> {

    // @Query("SELECT sp.seller FROM SellerProduct sp WHERE(sp.product.productId = :productId)")
    // List<Seller> findByProductId(Long productId);
    //
    // Optional<SellerProduct> findByProduct(Product product);

    @EntityGraph(attributePaths = {"seller"})
    Optional<SellerProduct> findByProductCode(String productCode);
}
