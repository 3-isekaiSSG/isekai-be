package com.isekai.ssgserver.member.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class FavoriteDelRequestDto {
	private List<FavoriteReqDto> favoriteDelList;
}
