package com.spring.careHeim.domain.awsS3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.domain.awsS3.model.FileInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import static com.spring.careHeim.config.BaseResponseStatus.POST_FAIL_S3;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AwsS3Service {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Autowired
    private final AmazonS3 amazonS3;
    @Value("${cloud.aws.s3.dir}")
    private String dir;

    public FileInfo uploadImage(MultipartFile image) throws BaseException {
        try {
            String fileName = createFileName(image.getOriginalFilename());
            String filePath = dir.concat(fileName).concat(getFileExtension(image.getOriginalFilename()));
            String fileUrl = amazonS3.getUrl(bucket, filePath).toString();

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(image.getSize());
            objectMetadata.setContentType(image.getContentType());

            try (InputStream inputStream = image.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, filePath, inputStream, objectMetadata)
                        .withCannedAcl(CannedAccessControlList.PublicRead));
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
            }

            FileInfo fileInfo = new FileInfo(fileName, fileUrl);

            return fileInfo;
        } catch (Exception e){
            System.out.println(e);
            throw new BaseException(POST_FAIL_S3);
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString();
    }

    private String getFileExtension(String fileName) {
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }
}
