package com.spring.careHeim.domain.clothes.document;

import com.spring.careHeim.domain.clothes.model.ClotheInfo;
import com.spring.careHeim.domain.common.BaseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Getter
@Builder
@Document(collection = "clothes")
public class ClotheDocument {
    @Id
    @Field("clotheId")
    private String clotheId;
    private String uuid;
    private Integer type;
    private Integer pattern;
    private List<String> colors;
    private List<String> features;
    private String nickName;
    private String image;
    private List<String> careInfos;
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;
    private DocumentStatus status;

    public ClotheDocument(ClotheInfo clotheInfo) {
        this.type = clotheInfo.getType();
        this.pattern = clotheInfo.getPtn();
        this.colors = clotheInfo.getColors();
        this.features = clotheInfo.getFeatures();
        this.nickName = clotheInfo.getNickName();
    }

    public enum DocumentStatus {
        ACTIVE, INACTIVE;
    }
}
