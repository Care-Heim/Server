package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.config.BaseResponseStatus;
import com.spring.careHeim.domain.awsS3.model.FileInfo;
import com.spring.careHeim.domain.clothes.document.ClotheDocument;
import com.spring.careHeim.domain.clothes.model.*;
import com.spring.careHeim.domain.users.UserRepository;
import com.spring.careHeim.domain.users.UserService;
import com.spring.careHeim.domain.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.bson.types.ObjectId;
import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.spring.careHeim.config.BaseResponseStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClotheService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ClotheDocumentRepository clotheDocumentRepository;

    public boolean hasSameClothe(User user, ClotheRequest clotheInfo) throws BaseException{
        Integer cnt = 0;
        if(clotheInfo.getFeatures() == null) {
            cnt = clotheDocumentRepository.countByUuidAndTypeAndPtnAndColorsAndNickName(user.getUuid(),
                    clotheInfo.getType(),
                    clotheInfo.getPtn(),
                    clotheInfo.getColors().toArray(new String[clotheInfo.getColors().size()]),
                    clotheInfo.getNickname());
        } else {
            cnt = clotheDocumentRepository.countByUuidAndTypeAndPtnAndColorsAndFeaturesAndNickName(user.getUuid(),
                    clotheInfo.getType(),
                    clotheInfo.getPtn(),
                    clotheInfo.getColors().toArray(new String[clotheInfo.getColors().size()]),
                    clotheInfo.getFeatures().toArray(new String[clotheInfo.getFeatures().size()]),
                    clotheInfo.getNickname());
        }

        if(cnt == null) {
            System.out.println("ERROR!");
            throw new BaseException(DATABASE_ERROR);
        }

        System.out.println("\n*****");
        System.out.println(cnt);
        System.out.println("****\n");


        if(cnt == 0) {
            return false;
        } else {
            return true;
        }
    }

    @Transactional
    public void addNewClothe(User user, ClotheRequest clotheInfo) throws BaseException {
        User nowUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_DONT_EXIST));

        if(hasSameClothe(user, clotheInfo)) {
            throw new BaseException(CLOTHE_ALREADY_EXIST);
        } else {
            ClotheDocument newClothe = new ClotheDocument(user.getUuid(), clotheInfo);

            clotheDocumentRepository.save(newClothe);
        }
    }

    public void addCareInfos(User user, CareInfo careInfo) throws BaseException {
        User nowUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_DONT_EXIST));

        ClotheDocument clotheDocument = clotheDocumentRepository.findById(new ObjectId(careInfo.getClotheId())).orElseThrow(() -> new BaseException(CLOTHE_DONT_EXIST));

        clotheDocument.addCareInfos(careInfo.getCareInfos());

        clotheDocumentRepository.save(clotheDocument);
    }

    public ClotheResponse findRecentClothe(User user) throws BaseException {
        User nowUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_DONT_EXIST));

        ClotheDocument clotheDocument = clotheDocumentRepository.findRecentClothe(nowUser.getUuid());

        if(clotheDocument == null) {
            throw new BaseException(RECENT_CLOTHE_DONT_EXIST);
        }

        if(clotheDocument.getCareInfos() != null) {
            throw new BaseException(CAREINFO_ALREADY_ENROLL);
        } else {
            ClotheResponse clotheResponse = ClotheResponse.builder()
                    .clotheId(String.valueOf(clotheDocument.getClotheId()))
                    .type(clotheDocument.getType())
                    .ptn(clotheDocument.getPattern())
                    .colors(clotheDocument.getColors())
                    .features(clotheDocument.getFeatures()).build();

            return clotheResponse;
        }
    }

    public ClotheResponse findClothe(User user, ClotheRequest clotheRequest) throws BaseException {
        User nowUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_DONT_EXIST));

        List<ClotheDocument> clotheDocuments = clotheDocumentRepository.findClothes(nowUser.getUuid(), clotheRequest.getType(),
                                                                            clotheRequest.getPtn(),
                                                                            clotheRequest.getColors().toArray(new String[0]),
                                                                            clotheRequest.getFeatures().toArray(new String[0]),
                                                                            clotheRequest.getNickname());

        if(clotheDocuments == null) {
            throw new BaseException(CLOTHE_DONT_EXIST);
        } else if(clotheDocuments.size() > 1) {
            throw new BaseException(CLOTHE_DUPLICATE);
        }

        ClotheDocument clotheDocument = clotheDocuments.get(0);

        ClotheResponse clotheResponse = ClotheResponse.builder()
                .clotheId(String.valueOf(clotheDocument.getClotheId()))
                .type(clotheDocument.getType())
                .ptn(clotheDocument.getPattern())
                .colors(clotheDocument.getColors())
                .features(clotheDocument.getFeatures())
                .nickname(clotheDocument.getNickname())
                .build();

        return clotheResponse;
    }

    // Clothe Segmentation 요청
    public SegmentResult requestSegClothe(FileInfo fileInfo) throws BaseException {
        // 요청을 보낼 uri 작성
        String uri = "http://localhost:10002/clothes";

        // 응답을 주고 받을 template 생성
        RestTemplate restTemplate = new RestTemplate();

        // Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // RequestBody 설정
        JSONObject requestBody = new JSONObject();
        requestBody.put("fileUrl", fileInfo.getFileUrl());
        requestBody.put("fileName", fileInfo.getFileName());

        // HttpEntity 설정
        HttpEntity entity = new HttpEntity<>(requestBody.toString(), headers);

        // 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(uri, entity, Map.class);

        // ResponseBody parsing
        if(!response.hasBody()) {
            throw new BaseException(FAIL_CLOTHE_SEG);
        }

        Map body = response.getBody();

        String fileUrl = body.get("fileUrl").toString();

        // file명, imageUrl, jsonUrl return
        SegmentResult result = new SegmentResult(fileInfo.getFileName(), fileInfo.getFileUrl(), fileUrl);

        return result;
    }

    /** DefaultUser 처리용 override, 차후 User 구별 시 삭제 예정 **/

    public void addNewClothe(ClotheRequest clotheInfo) throws BaseException {
        User defaultUser = userService.getDefaultUser();
        addNewClothe(defaultUser, clotheInfo);
    }

    public void addCareInfos(CareInfo careInfo) throws BaseException {
        User defaultuser = userService.getDefaultUser();
        addCareInfos(defaultuser, careInfo);
    }

    public ClotheResponse findRecentClothe() throws BaseException {
        User defaultuser = userService.getDefaultUser();
        return findRecentClothe(defaultuser);
    }

    public ClotheResponse findClothe(ClotheRequest clotheRequest) throws BaseException {
        User defaultuser = userService.getDefaultUser();
        return findClothe(defaultuser, clotheRequest);
    }
}