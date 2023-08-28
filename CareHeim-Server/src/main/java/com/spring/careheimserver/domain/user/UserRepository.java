package com.spring.careHeim.domain.user;

import com.spring.careHeim.domain.user.document.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
}
