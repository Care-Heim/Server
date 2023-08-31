package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.config.BaseResponseStatus;
import com.spring.careHeim.domain.clothes.document.ClotheDocument;
import com.spring.careHeim.domain.clothes.model.ClotheInfo;
import com.spring.careHeim.domain.users.UserRepository;
import com.spring.careHeim.domain.users.UserService;
import com.spring.careHeim.domain.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.spring.careHeim.config.BaseResponseStatus.CLOTHE_ALREADY_EXIST;
import static com.spring.careHeim.config.BaseResponseStatus.DATABASE_ERROR;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClotheService {
    private final UserService userService;
    private final UserRepository userRepository;
    private final ClotheDocumentRepository clotheDocumentRepository;
    public boolean hasSameClothe(User user, ClotheInfo clotheInfo) throws BaseException{
        Integer cnt = 0;
        if(clotheInfo.getFeatures() == null) {
            cnt = clotheDocumentRepository.countByUuidAndTypeAndPtnAndColorsAndNickName(user.getUuid(),
                    clotheInfo.getType(),
                    clotheInfo.getPtn(),
                    clotheInfo.getColors().toArray(new String[clotheInfo.getColors().size()]),
                    clotheInfo.getNickName());
        } else {
            cnt = clotheDocumentRepository.countByUuidAndTypeAndPtnAndColorsAndFeaturesAndNickName(user.getUuid(),
                    clotheInfo.getType(),
                    clotheInfo.getPtn(),
                    clotheInfo.getColors().toArray(new String[clotheInfo.getColors().size()]),
                    clotheInfo.getFeatures().toArray(new String[clotheInfo.getFeatures().size()]),
                    clotheInfo.getNickName());
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

    public void addNewClothe(ClotheInfo clotheInfo) throws BaseException {
        User defaultUser = userService.getDefaultUser();
        addNewClothe(defaultUser, clotheInfo);
    }

    @Transactional
    public void addNewClothe(User user, ClotheInfo clotheInfo) throws BaseException {
        User nowUser = userRepository.findById(user.getUserId()).orElseThrow(() -> new BaseException(BaseResponseStatus.USERS_DONT_EXIST));

        if(hasSameClothe(user, clotheInfo)) {
            throw new BaseException(CLOTHE_ALREADY_EXIST);
        } else {
            ClotheDocument newClothe = new ClotheDocument(user.getUuid(), clotheInfo);

            clotheDocumentRepository.save(newClothe);
        }
    }
}