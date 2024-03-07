package com.isekai.ssgserver.sample.controller;

import com.isekai.ssgserver.sample.dto.SampleResponseDto;
import com.isekai.ssgserver.sample.service.SampleService;
import com.isekai.ssgserver.util.MessageResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sample")
@Tag(name = "Sample")
public class SampleController {

    private final SampleService sampleService;

    /**
     * ResponseEntity를 이용 - HttpStatus 사용 + message 구분 가능
     */
    @GetMapping("/http/{sample_pk}")
    @Operation(summary = "HttpStatus 사용", description = "Sample 응답값을 확인 할 수 있습니다.")
    public ResponseEntity<?> test(
            @PathVariable @Parameter(example = "1") Long sample_pk) {

        if (sample_pk != 1) {
            return new ResponseEntity<>(new MessageResponse("조회를 실패했습니다."),
                    HttpStatus.BAD_REQUEST);
        }
        SampleResponseDto sample = sampleService.getSampleDetail();
        return new ResponseEntity<>(sample, HttpStatus.OK);
    }

}
