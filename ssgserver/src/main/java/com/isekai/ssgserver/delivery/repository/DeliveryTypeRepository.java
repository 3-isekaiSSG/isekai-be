package com.isekai.ssgserver.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.isekai.ssgserver.delivery.entity.DeliveryType;

public interface DeliveryTypeRepository extends JpaRepository<DeliveryType, Long> {

}
