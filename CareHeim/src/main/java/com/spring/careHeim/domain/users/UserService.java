package com.spring.careHeim.domain.users;

import com.spring.careHeim.config.BaseException;
import com.spring.careHeim.config.BaseResponseStatus;
import com.spring.careHeim.domain.clothes.ClotheDocumentRepository;
import com.spring.careHeim.domain.clothes.ClotheService;
import com.spring.careHeim.domain.clothes.document.ClotheDocument;
import com.spring.careHeim.domain.clothes.model.ClotheInfo;
import com.spring.careHeim.domain.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.spring.careHeim.config.BaseResponseStatus.CLOTHE_ALREADY_EXIST;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User getDefaultUser() {
        Optional<User> nowUser = userRepository.findById(1L);
        System.out.println("**********");
        System.out.println("findUser!");
        System.out.println("**********");

        if(nowUser == null) {
            return null;
        } else {
            return nowUser.get();
        }
    }
}