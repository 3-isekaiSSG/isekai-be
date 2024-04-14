package com.isekai.ssgserver.deliveryAddress.controller;

import java.util.List;

import com.isekai.ssgserver.common.dto.DataIdDto;
import com.isekai.ssgserver.deliveryAddress.dto.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.isekai.ssgserver.deliveryAddress.service.DeliveryAddressService;
import com.isekai.ssgserver.util.jwt.JwtProvider;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/delivery-addresses")
@Tag(name = "DeliveryAddress", description = "배송지 관련 API document")
public class DeliveryAddressController {

	private final JwtProvider jwtProvider;
	private final DeliveryAddressService deliveryAddressService;

	@GetMapping("/{deliveryAddressId}/nickname")
	@Operation(summary = "배송지 별칭 조회 - 회원", description = "사용자 지정 별칭 조회")
	public ResponseEntity<DeliveryAddressNicknameDto> getDeliveryAddressNickname(
			@RequestHeader(value = "Authorization") String token,
			@PathVariable Long deliveryAddressId) {

		String uuid = jwtProvider.getUuid(token);

		DeliveryAddressNicknameDto nicknameDto = deliveryAddressService.getDeliveryAddressNickname(uuid, deliveryAddressId);
		return ResponseEntity.ok(nicknameDto);
	}

	@GetMapping("/{deliveryAddressId}")
	@Operation(summary = "배송지 정보 조회 - 회원")
	public ResponseEntity<DeliveryAddressInfoDto> getMemberDeliveryAddressInfo(
			@RequestHeader(value = "Authorization") String token,
			@PathVariable Long deliveryAddressId) {

		String uuid = jwtProvider.getUuid(token);

		DeliveryAddressInfoDto deliveryAddressInfoDto = deliveryAddressService.getDeliveryAddressInfo(uuid,
				deliveryAddressId);
		return ResponseEntity.ok(deliveryAddressInfoDto);
	}

	@GetMapping("/{deliveryAddressId}/nonmember")
	@Operation(summary = "배송지 정보 조회 - 비회원")
	public ResponseEntity<DeliveryAddressInfoDto> getNonMemberDeliveryAddressInfo(@PathVariable Long deliveryAddressId) {

		DeliveryAddressInfoDto deliveryAddressInfoDto = deliveryAddressService.getDeliveryAddressInfo(null,
				deliveryAddressId);
		return ResponseEntity.ok(deliveryAddressInfoDto);
	}

	@GetMapping("/members")
	@Operation(summary = "배송지 리스트 조회 - 회원", description = "관리하고 있는 배송지 리스트 조회")
	public ResponseEntity<List<DeliveryAddressListDto>> getMembersDeliveryAddressList(
			@RequestHeader(value = "Authorization") String token
	) {

		String uuid = jwtProvider.getUuid(token);

		List<DeliveryAddressListDto> addressList = deliveryAddressService.getMembersDeliveryAddressList(uuid);
		return ResponseEntity.ok(addressList);
	}

	@DeleteMapping("/{deliveryAddressId}")
	@Operation(summary = "배송지 삭제 - 회원", description = "배송지 is_deleted = true")
	public ResponseEntity<Void> softDeleteDeliveryAddress(
			@RequestHeader(value = "Authorization") String token,
			@PathVariable Long deliveryAddressId) {

		String uuid = jwtProvider.getUuid(token);

		deliveryAddressService.softDeleteDeliveryAddress(uuid, deliveryAddressId);

		return ResponseEntity.ok().build();
	}


	@PostMapping("")
	@Operation(summary = "배송지 정보 생성 - 회원", description = "배송지 정보 추가")
	public ResponseEntity<DataIdDto> createMemberDeliveryAddress(
			@RequestHeader(value = "Authorization") String token,
			@RequestBody DeliveryAddressCreateDto deliveryAddressCreateDto) {

		String uuid = jwtProvider.getUuid(token);

		DataIdDto createdAddress = deliveryAddressService.createDeliveryAddress(uuid, deliveryAddressCreateDto);
		return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
	}

	@PostMapping("/nonmember")
	@Operation(summary = "배송지 정보 생성 - 비회원", description = "배송지 정보 추가")
	public ResponseEntity<DataIdDto> createNonMemberDeliveryAddress(
			@RequestBody DeliveryAddressCreateDto deliveryAddressCreateDto) {

		DataIdDto createdAddress = deliveryAddressService.createDeliveryAddress(null, deliveryAddressCreateDto);
		return new ResponseEntity<>(createdAddress, HttpStatus.CREATED);
	}

	@PutMapping("/{deliveryAddressId}")
	@Operation(summary = "배송지 정보 수정 - 회원", description = "배송지 정보 수정")
	public ResponseEntity<DataIdDto> updateMemberDeliveryAddress(
			@RequestHeader(value = "Authorization") String token,
			@PathVariable Long deliveryAddressId,
			@RequestBody DeliveryAddressCreateDto deliveryAddressCreateDto) {

		String uuid = jwtProvider.getUuid(token);

		DataIdDto updatedAddress = deliveryAddressService.updateDeliveryAddress(uuid, deliveryAddressId, deliveryAddressCreateDto);
		return ResponseEntity.ok(updatedAddress);
	}

	@PatchMapping("/{deliveryAddressId}/default")
	@Operation(summary = "기본 배송지 설정 - 회원", description = "기본배송지 기존-false, 신규-true")
	public ResponseEntity<DataIdDto> setDefaultDeliveryAddress(
			@RequestHeader(value = "Authorization") String token,
			@PathVariable Long deliveryAddressId) {

		String uuid = jwtProvider.getUuid(token);

		DataIdDto newDefaultDeliveryAddress = deliveryAddressService.setDefaultDeliveryAddress(uuid, deliveryAddressId);
		return ResponseEntity.ok(newDefaultDeliveryAddress);
	}



}
