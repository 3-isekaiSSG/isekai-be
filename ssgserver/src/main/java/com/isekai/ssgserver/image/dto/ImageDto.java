package com.isekai.ssgserver.image.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class ImageDto {

	private byte isThumbnail;
	private byte seq;
	private String imageUrl;
}
