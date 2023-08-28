package com.spring.careHeim.domain.clothes.document;

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
@Document("clothe")
public class Clothe extends BaseTime{
    @Id
    private String clotheId;
    private Integer type;
    private Integer pattern;
    private List<String> colors;
    private List<String> features;
    private String nickName;
    private String image;
}
