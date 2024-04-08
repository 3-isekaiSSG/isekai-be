package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.enums.FavoriteDivision;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteReqDto {
	private String identifier;
	private FavoriteDivision division;
}
