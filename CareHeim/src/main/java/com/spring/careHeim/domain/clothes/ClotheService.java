package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.domain.clothes.model.ClotheInfo;
import com.spring.careHeim.domain.users.UserRepository;
import com.spring.careHeim.domain.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClotheService {
    private final UserRepository userRepository;
    private final ClotheDocumentRepository clotheDocumentRepository;
    public boolean hasSameClothe(User user, ClotheInfo clotheInfo) {
        int cnt = clotheDocumentRepository.countByUuidAndTypeAndPtnAndColorsAndFeaturesAndNickName(user.getUuid(),
                clotheInfo.getType(),
                clotheInfo.getPtn(),
                clotheInfo.getColors(),
                clotheInfo.getFeatures(),
                clotheInfo.getNickName());

        if(cnt == 0) {
            return false;
        } else {
            return true;
        }
    }
}