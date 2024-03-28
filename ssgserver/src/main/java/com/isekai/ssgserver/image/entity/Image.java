package com.isekai.ssgserver.image.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@ToString
@Getter
@Table(name = "image")
public class Image {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long imageId;

	@Column(name = "image_url", nullable = false)
	private String imageUrl;

	private String description;

	@Column(name = "is_thumbnail")
	private byte isThumbnail;

	@Column(name = "created_at")
	private LocalDateTime createdAt;

	private byte seq;

	@Column(name = "product_code")
	private String productCode;

	// 연관 관계

	// @PrePersist
}
