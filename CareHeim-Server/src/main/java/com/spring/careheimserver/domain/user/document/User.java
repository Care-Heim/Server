package com.spring.careHeim.domain.user.document;

import com.spring.careHeim.domain.clothes.document.Clothe;
import com.spring.careHeim.domain.common.BaseTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@Document("user")
public class User extends BaseTime {
    @Id
    private String userId;
    private List<Clothe> clothes;
}
