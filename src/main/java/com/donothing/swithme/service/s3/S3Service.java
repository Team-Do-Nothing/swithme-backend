package com.donothing.swithme.service.s3;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, String folderName) throws IOException {
        // 파일 이름 생성
        if (folderName == null || folderName.isBlank()) {
            throw new IllegalArgumentException("폴더 이름은 비어 있을 수 없습니다.");
        }

        String fileName = generateFileName(file.getOriginalFilename(), folderName);

        try {
            // 파일 메타데이터 설정
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(file.getSize());
            metadata.setContentType(file.getContentType());

            // S3 파일 업로드
            amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);
        } catch (AmazonServiceException e) {
            throw new IllegalStateException("AWS S3 서비스 오류: " + e.getMessage(), e);
        } catch (AmazonClientException e) {
            throw new IllegalStateException("AWS 클라이언트 오류: " + e.getMessage(), e);
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 업로드 중 오류가 발생했습니다.", e);
        }

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /**
     * 파일명 난수화, UUID 활용
     */
    public String generateFileName(String fileName, String folderName) {
        return folderName + "/" + UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    /**
     * "." 존재 유무 판단 후 확장자 반환
     */
    public String getFileExtension(String fileName) {
        try {
            String extension = fileName.substring(fileName.lastIndexOf("."));
            if (!List.of(".jpg", ".jpeg", ".png", ".gif").contains(extension.toLowerCase())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "지원하지 않는 파일 형식입니다.");
            }
            return extension;
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    /**
     *  S3에 있는 파일 삭제
     */
    public void deleteFile(String imageAddress) throws Exception {
        String key = getKeyFromImageAddress(imageAddress);
        try {
            // 파일 삭제 요청
            amazonS3Client.deleteObject(new DeleteObjectRequest(bucket, key));
        } catch (AmazonServiceException e) {
            log.info(e.getErrorMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 삭제 중 오류가 발생했습니다.", e);
        }
    }

    /**
     * @param imageAddress
     * @return URL key value
     */
    private String getKeyFromImageAddress(String imageAddress) {
        try {
            URL url = new URL(imageAddress);
            String decodingKey = URLDecoder.decode(url.getPath(), "UTF-8");
            return decodingKey.substring(1); // 맨 앞의 '/' 제거
        } catch (UnsupportedEncodingException | MalformedURLException e){
            throw new RuntimeException(e);
        } catch (AmazonServiceException e) {
            if ("NoSuchKey".equals(e.getErrorCode())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "파일을 찾을 수 없습니다.");
            }
            throw new IllegalStateException("AWS S3 서비스 오류: " + e.getMessage(), e);
        }
    }
}
