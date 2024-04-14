package com.isekai.ssgserver.s3.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.isekai.ssgserver.s3.service.S3Service;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/aws")
@RequiredArgsConstructor
public class S3Controller {
	private final S3Service s3Service;

	@GetMapping("/")
	@Operation(summary = "S3 연동 테스트", description = "S3 isekai-s3 버킷의 객체를 가져와서 출력(서버)")
	public ResponseEntity<Void> s3Test() {
		s3Service.listBucketObjects();
		return ResponseEntity.ok().build();
	}

	@GetMapping("/url")
	@Operation(summary = "S3 Pre-signed url 생성", description = "S3 bucket에 별도 권한없이 이미지 PUT 가능한 미리 서명된 URL 발급")
	public ResponseEntity<String> getPresignedUrl() {
		String uploadUrl = s3Service.createPresignedUrl("test", null);
		return ResponseEntity.ok(uploadUrl);
	}
}
