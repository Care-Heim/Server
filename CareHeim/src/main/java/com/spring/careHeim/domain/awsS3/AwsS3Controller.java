package com.spring.careHeim.domain.awsS3;

import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/s3")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;

    public AwsS3Controller(AwsS3Service awsS3Service) {
        this.awsS3Service = awsS3Service;
    }

    @ResponseBody
    @PostMapping("/image")
    public BaseResponse<String> uploadImage(@RequestParam(name = "image") MultipartFile image) throws BaseException {
        String fileUrl = awsS3Service.uploadImage(image);
        return new BaseResponse<>(fileUrl);
    }
}
