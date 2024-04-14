package com.isekai.ssgserver.member.entity;

import com.isekai.ssgserver.common.BaseEntity;

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
@ToString
@Getter
@Table(name = "favorite")
public class Favorite extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "favorite_id")
	private Long favoriteId;

	@Column(nullable = false)
	private String uuid;

	@Column(name = "division", nullable = false)
	private byte division;

	@Column(name = "identifier", nullable = false)
	private Long identifier;

	@Builder
	public Favorite(String uuid, byte division, Long identifier) {
		this.uuid = uuid;
		this.division = division;
		this.identifier = identifier;
	}
}
