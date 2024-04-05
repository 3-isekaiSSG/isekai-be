package com.isekai.ssgserver.deliveryAddress.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.deliveryAddress.entity.DeliveryAddress;

@Repository
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Long> {

	List<DeliveryAddress> findAllByMemberId(Long MemberId);
}
