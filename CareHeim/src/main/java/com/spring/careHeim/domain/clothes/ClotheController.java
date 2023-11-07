package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.config.BaseResponse;
import com.spring.careHeim.domain.clothes.model.CareInfo;
import com.spring.careHeim.domain.clothes.model.ClotheRequest;
import com.spring.careHeim.domain.clothes.model.ClotheResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.spring.careHeim.config.BaseResponseStatus.CREATED;
import static com.spring.careHeim.config.BaseResponseStatus.SUCCESS;

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

}