package com.isekai.ssgserver.deliveryAddress.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.stereotype.Service;

import com.isekai.ssgserver.deliveryAddress.controller.DeliveryAddressInfoDto;
import com.isekai.ssgserver.deliveryAddress.dto.DeliveryAddressListDto;
import com.isekai.ssgserver.deliveryAddress.dto.DeliveryAddressNicknameDto;
import com.isekai.ssgserver.deliveryAddress.entity.DeliveryAddress;
import com.isekai.ssgserver.deliveryAddress.repository.DeliveryAddressRepository;
import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.member.repository.MemberRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class DeliveryAddressService {

	private final DeliveryAddressRepository deliveryAddressRepository;
	private final MemberRepository memberRepository;

	public DeliveryAddressNicknameDto getDeliveryAddressNickname(Long deliveryAddressId) {

		return deliveryAddressRepository.findById(deliveryAddressId)
			.map(da -> DeliveryAddressNicknameDto.builder()
				.nickname(da.getNickname())
				.build())
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
	}

	public DeliveryAddressInfoDto getDeliveryAddressInfo(String uuid, Long deliveryAddressId) {

		DeliveryAddress deliveryAddress = deliveryAddressRepository.findById(deliveryAddressId)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));

		if (deliveryAddress.getMemberId() != -1L) { // 회원의 배송지인 경우
			Long savedMemberId = memberRepository.findByUuidAndIsWithdraw(uuid, (byte)0)
				.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))
				.getMemberId();

			if (!savedMemberId.equals(deliveryAddress.getMemberId())) {
				throw new CustomException(ErrorCode.NO_AUTHORITY);
			}
		}

		return DeliveryAddressInfoDto.builder()
			.nickname(deliveryAddress.getNickname())
			.name(deliveryAddress.getName())
			.cellphone(deliveryAddress.getCellphone())
			.telephone(deliveryAddress.getTelephone())
			.zipcode(deliveryAddress.getZipcode())
			.address(deliveryAddress.getAddress())
			.isDefault(deliveryAddress.isDefault())
			.isDeleted(deliveryAddress.isDeleted())
			.orderHistory(deliveryAddress.isOrderHistory())
			.build();

	}

	public List<DeliveryAddressListDto> getMembersDeliveryAddressList(String uuid) {

		AtomicInteger id = new AtomicInteger(0);

		Long memberId = memberRepository.findByUuidAndIsWithdraw(uuid, (byte)0)
			.orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_USER))
			.getMemberId();

		return deliveryAddressRepository.findAllByMemberId(memberId)
			.stream()
			.map(da -> DeliveryAddressListDto.builder()
				.id(id.getAndIncrement())
				.deliveryAddressId(da.getDeliveryAddressId())
				.build())
			.toList();

	}
}
