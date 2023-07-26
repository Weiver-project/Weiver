package weiver.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import weiver.config.CommonUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public String uploadFileV1(MultipartFile multipartFile) throws FileUploadException, FileNotFoundException {
        // 업로드된 파일이 비어 있는지 확인
        if (multipartFile.isEmpty()) {
            throw new FileNotFoundException("업로드된 파일이 비어 있습니다.");
        }

        String fileName = CommonUtils.buildFileName(multipartFile.getOriginalFilename());

        try (InputStream inputStream = multipartFile.getInputStream()) {
            // 파일 데이터를 바이트 배열로 변환
            byte[] fileBytes = inputStream.readAllBytes();

            // S3에 파일 업로드
            s3Client.putObject(PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(multipartFile.getContentType()) // 컨텐츠 유형 (옵션)
                    .build(), RequestBody.fromBytes(fileBytes));

            // 파일 업로드 완료 후, 업로드된 파일의 URL 등을 반환 (예를 들면 S3에 업로드한 파일의 URL을 반환)

            String fileUrl = s3Client.utilities().getUrl(builder -> builder.bucket(bucketName).key(fileName)).toExternalForm();
            return fileUrl;
            
        } catch (IOException | S3Exception e) {
            throw new FileUploadException("파일 처리 중 오류 발생: " + e.getMessage());
        }
    }
  }
