package com.spring.careHeim.domain.users;

import com.spring.careHeim.domain.users.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;


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