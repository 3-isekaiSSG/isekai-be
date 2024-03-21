package com.isekai.ssgserver.member.entity;

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
@Table(name = "member_social")
public class MemberSocial {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_social_id")
	private Long memberSocialId;

	@Column(nullable = false)
	private String uuid;

	@Column(name = "member_social_code", nullable = false)
	private String memberSocialCode;

	@Column(name = "socail_division_code", nullable = false)
	private byte socailDivisionCode;
}
