package com.isekai.ssgserver.member.dto;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class FavoriteCountDto {
	private byte id;
	private Long data;

	public FavoriteCountDto(byte id, Long data) {
		this.id = id;
		this.data = data;
	}
}
