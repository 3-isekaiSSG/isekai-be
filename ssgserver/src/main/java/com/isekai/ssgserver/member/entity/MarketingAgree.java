package com.isekai.ssgserver.member.entity;

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
@Table(name = "marketing_agree")
public class MarketingAgree {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String uuid;

	@Column(name = "member_agree_id")
	private Long memberAgree;

	@Column(name = "email", nullable = false)
	private byte email;

	@Column(name = "sms", nullable = false)
	private byte sms;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
