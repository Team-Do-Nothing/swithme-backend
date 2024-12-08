package com.donothing.swithme.controller;

import com.donothing.swithme.domain.ImageApiType;
import com.donothing.swithme.dto.image.ImageUploadResponseDto;
import com.donothing.swithme.dto.response.ResponseDto;
import com.donothing.swithme.service.s3.S3Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.util.*;

@Slf4j
@RequestMapping("/api/v1/image")
@RestController
@RequiredArgsConstructor
@Api(tags = {"이미지 관련 API"})
public class ImageController {

    private final S3Service s3Service;

    @PostMapping("{type}")
    @ApiOperation(value = "이미지 파일 업로드")
    public ResponseEntity<ResponseDto<ImageUploadResponseDto>> uploadImageFile(
            @ApiParam(value = "예시 : STUDY/CHALLENGE/PROFILE")
            @PathVariable String type,
            @RequestPart("file") MultipartFile multipartFile) throws IOException {

        if (!ImageApiType.isValid(type)) {
            throw new IllegalStateException(type + "은 존재하지 않는 타입입니다.");
        }

        String fileUrl = s3Service.uploadFile(multipartFile, type);

        ImageUploadResponseDto responseDto = new ImageUploadResponseDto();
        responseDto.setImageS3Url(fileUrl);

        return new ResponseEntity<>(new ResponseDto<>(201, "이미지 파일 업로드 성공", responseDto),
                HttpStatus.OK);
    }

    @DeleteMapping("/{fileName}")
    @ApiOperation(value = "이미지 파일 삭제", notes = "주어진 경로에 해당하는 이미지 파일을 삭제하는 API 입니다.")
    public ResponseEntity<?> deleteImageFile(@RequestParam("fileName") String fileName) throws Exception {

        s3Service.deleteFile(fileName);

        return new ResponseEntity<>(new ResponseDto<>(204, "이미지 파일 삭제 성공", fileName),
                HttpStatus.NO_CONTENT);
    }
}
