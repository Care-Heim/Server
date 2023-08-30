package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.config.BaseResponse;
import com.spring.careHeim.domain.clothes.model.ClotheInfo;
import com.spring.careHeim.domain.users.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import static com.spring.careHeim.config.BaseResponseStatus.SUCCESS;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/clothes")
public class ClotheController {
    private final UserService userService;

    /**
     * 의류등록 API
     * [POST] /clothes/enroll
     * @return BaseResponse<SinglePostRes>
     **/
    @ResponseBody
    @PostMapping("/enroll")
    public BaseResponse<String> addNewClothe(@RequestBody ClotheInfo clotheInfo) {
        try {
            userService.addNewClothe(clotheInfo);

            return new BaseResponse<>(SUCCESS);
        } catch (BaseException e) {
            return new BaseResponse<>(e.getStatus());
        }
    }
}