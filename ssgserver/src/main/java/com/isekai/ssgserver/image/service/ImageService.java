package com.isekai.ssgserver.image.service;

import com.isekai.ssgserver.exception.common.CustomException;
import com.isekai.ssgserver.exception.constants.ErrorCode;
import com.isekai.ssgserver.image.dto.ImageDto;
import com.isekai.ssgserver.image.entity.Image;
import com.isekai.ssgserver.image.repository.ImageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ImageService {

    private final ImageRepository imageRepository;

    public ImageDto getThumbnailImageByProduct(String productCode) {

        return imageRepository.findByProductCodeAndIsThumbnail(productCode, 1)
                .map(i -> ImageDto.builder()
                        .isThumbnail(i.getIsThumbnail())
                        .seq(i.getSeq())
                        .imageUrl(i.getImageUrl())
                        .build())
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ENTITY));
    }

    public List<ImageDto> getImagesByProduct(String productCode) {

        return imageRepository.findAllByProductCode(productCode)
                .stream()
                .map(i -> ImageDto.builder()
                        .isThumbnail(i.getIsThumbnail())
                        .seq(i.getSeq())
                        .imageUrl(i.getImageUrl())
                        .build())
                .toList();
    }

}
