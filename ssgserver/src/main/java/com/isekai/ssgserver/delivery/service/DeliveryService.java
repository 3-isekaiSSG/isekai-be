package com.isekai.ssgserver.delivery.service;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.delivery.dto.DeliveryStatusCountDto;
import com.isekai.ssgserver.delivery.enums.DeliveryStatusCode;
import com.isekai.ssgserver.delivery.repository.DeliveryRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryService {

	private final DeliveryRepository deliveryRepository;
	private final MemberRepository memberRepository;

	public DeliveryStatusCountDto getOrderCountByStatus(String uuid, int status) {

		// 멤버 있는지 확인
		if (!memberRepository.existsByUuidAndIsWithdraw(uuid, (byte)0)) {
			throw new CustomException(ErrorCode.NOT_FOUND_USER);
		}

		return DeliveryStatusCountDto.builder()
			.status(status)
			.statusName(DeliveryStatusCode.getNameByCode(status))
			.count((int)deliveryRepository.countByUuidAndStatus(uuid, status))
			.build();

	}
}
