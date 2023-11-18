package com.spring.careHeim.domain.awsS3;

import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.config.BaseResponse;
import com.spring.careHeim.config.BaseResponseStatus;
import com.spring.careHeim.domain.awsS3.model.FileInfo;
import com.spring.careHeim.domain.clothes.ClotheService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/s3")
public class AwsS3Controller {
    private final AwsS3Service awsS3Service;
    private final ClotheService clotheService;

    @ResponseBody
    @PostMapping("/image")
    public BaseResponse<String> uploadImage(@RequestParam(name = "image") MultipartFile image) throws BaseException {
        FileInfo fileInfo = awsS3Service.uploadImage(image);
        return new BaseResponse<>(BaseResponseStatus.SUCCESS);
    }

    @ResponseBody
    @PostMapping("/test")
    public BaseResponse<String> testImage(@RequestParam(name = "image") MultipartFile image) {
        try {
            FileInfo fileInfo = awsS3Service.uploadImage(image);
            String result = clotheService.requestSegClothe(fileInfo).toString();

            return new BaseResponse<>(result);
        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
    }
}
