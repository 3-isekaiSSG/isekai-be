package com.isekai.ssgserver.member.dto;

import com.isekai.ssgserver.member.enums.FavoriteDivision;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@ToString
public class FavoritePutReqDto {
	private String identifier;
	private String uuid;
	private FavoriteDivision division;
}
