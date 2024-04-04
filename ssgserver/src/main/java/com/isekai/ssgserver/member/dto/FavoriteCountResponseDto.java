package com.isekai.ssgserver.member.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
public class FavoriteCountResponseDto {
	private List<FavoriteCountDto> favoriteCountList;
}
