package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.enums.FavoriteDivision;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class FavoriteResDto {
	private Long favoriteId;
	private FavoriteDivision division;
	private Long identifier;
}
