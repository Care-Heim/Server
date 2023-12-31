package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.config.BaseResponse;
import com.spring.careHeim.domain.clothes.model.CareInfo;
import com.spring.careHeim.domain.clothes.model.ClotheInfo;
import com.spring.careHeim.domain.clothes.model.ClotheRequest;
import com.spring.careHeim.domain.clothes.model.ClotheResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.spring.careHeim.config.BaseResponseStatus.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes")
public class ClotheController {
    private final ClotheService clotheService;

    /**
     * 의류등록 API
     * [POST] /clothes/enroll
     * @return BaseResponse<SinglePostRes>
     */
    @ResponseBody
    @PostMapping("/enroll")
    public BaseResponse<String> addNewClothe(@RequestBody ClotheRequest clotheInfo) {
        try {
            clotheService.addNewClothe(clotheInfo);

            return new BaseResponse<>(CREATED);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 세탁정보 저장 API
     *  [POST] /clothes/careInfos/enroll
     */
    @ResponseBody
    @PostMapping("/careInfos/enroll")
    public BaseResponse<String> addCareInfos(@RequestBody CareInfo careInfo) {
        try {
            clotheService.addCareInfos(careInfo);

            return new BaseResponse<>(CREATED);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 의류 정보 요청 API
     *  [GET] /clothes
     */
    @ResponseBody
    @GetMapping
    public BaseResponse<ClotheResponse> requestRecentClotheInfos() {
        try {
            ClotheResponse clotheResponse = clotheService.findRecentClothe();

            return new BaseResponse<>(clotheResponse);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    /**
     * 의류 정보 요청 API
     *  [GET] /clothes?type=0&ptn=0&colors={색상}&features={특징}
     */
    @ResponseBody
    @GetMapping(params = {"type", "ptn", "colors", "features"})
    public BaseResponse<ClotheResponse> requestClotheInfos(@RequestParam("type") int type,
                                                           @RequestParam("ptn") int ptn,
                                                           @RequestParam("colors") List<String> colors,
                                                           @RequestParam("features") List<String> features) {
        try {
            ClotheRequest clotheRequest = ClotheRequest.builder().type(type).ptn(ptn).colors(colors).features(features).build();
            ClotheResponse clotheResponse = clotheService.findClothe(clotheRequest);

            if(clotheResponse == null) {
                return new BaseResponse<>(CLOTHE_DONT_EXIST);
            }

            return new BaseResponse<ClotheResponse>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }

    @ResponseBody
    @PostMapping("/extract")
    public BaseResponse<List<ClotheInfo>> extractFeatue(@RequestParam(name = "image") MultipartFile image) {
        try {
            List<ClotheInfo> clotheInfos = clotheService.getFeatures(image);

            return new BaseResponse<>(clotheInfos);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        } catch (IOException e) {
            return new BaseResponse<>(FAILED_TO_LOGIN);
        }
    }

    @ResponseBody
    @PostMapping("/careInfos")
    public BaseResponse<List<ClotheResponse>> testImage(@RequestParam(name = "image") MultipartFile image) throws Exception{
        try {
            System.out.println(image.getContentType());
            List<ClotheResponse> clothes = clotheService.getCareInfos(image);

            return new BaseResponse<>(clothes);
        } catch (BaseException e) {
            throw new RuntimeException(e);
        }
    }
}