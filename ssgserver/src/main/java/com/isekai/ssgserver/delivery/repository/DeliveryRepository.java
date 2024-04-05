package com.isekai.ssgserver.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.isekai.ssgserver.delivery.entity.Delivery;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

	long countByUuidAndStatus(String uuid, int status);
}
