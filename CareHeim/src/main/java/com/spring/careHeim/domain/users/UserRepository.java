package com.spring.careHeim.domain.users;

import com.spring.careHeim.domain.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
