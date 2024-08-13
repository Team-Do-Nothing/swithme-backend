package com.donothing.swithme.service.s3;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Service {

    private final AmazonS3Client amazonS3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadFile(MultipartFile file, String pathName) throws IOException {
//        Optional<File> uploadFile = null;
        File uploadFile = null;
        try {
            uploadFile = convert(file)
                    .orElseThrow(() -> new IllegalArgumentException("[error]: MultipartFIle -> 파일 변환 실패"));
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }

        String fileName = pathName + "/" + UUID.randomUUID();
        System.out.println(uploadFile + "<< uploadFile");
        String uploadImageUrl = putS3(uploadFile, fileName);
        removeNewFile(uploadFile);

//        return "uploadImageUrl";
        return uploadImageUrl;
    }

    /**
     *  S3로 업로드
     * @return 업로드 경로
     */
    public String putS3(File uploadFile, String fileName) {
        System.out.println("???bucket > " + bucket);
        amazonS3Client.putObject(new PutObjectRequest(bucket, fileName, uploadFile)
                .withCannedAcl(CannedAccessControlList.PublicRead));

        return amazonS3Client.getUrl(bucket, fileName).toString();
    }

    /**
     *  S3에 있는 파일 삭제
     */
    public void deleteS3(String filePath) throws Exception {
        try {
            String key = filePath.substring(56); // 폴더/파일.확장자

            try {
                amazonS3Client.deleteObject(bucket, key);
            } catch (AmazonServiceException e) {
                log.info(e.getErrorMessage());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        log.info("[S3Uploader] : S3에 있는 파일 삭제");
    }

    /**
     *  로컬에 파일 업로드 및 변환
     */
    private void removeNewFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("[파일 업로드] : 파일 삭제 성공");
            return;
        }
        log.info("[파일 업로드] : 파일 삭제 실패");
    }

    /**
     *  로컬에 파일 업로드 및 변환
     */
    private Optional<File> convert(MultipartFile file) throws IOException {
        String dirPath = System.getProperty("user.dir") + "/challenge/" + file.getOriginalFilename();
        File convertFile = new File(dirPath);

        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }

        return Optional.empty();
    }
}
