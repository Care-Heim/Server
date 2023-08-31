package com.spring.careHeim.domain.clothes.document;

import com.spring.careHeim.domain.clothes.model.ClotheInfo;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
@Document(collection = "clothes")
public class ClotheDocument {
    @Id
    private ObjectId clotheId;
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

    public ClotheDocument(String uuid, ClotheInfo clotheInfo) {
        this.uuid = uuid;
        this.type = clotheInfo.getType();
        this.pattern = clotheInfo.getPtn();
        this.colors = clotheInfo.getColors();
        this.features = clotheInfo.getFeatures();
        this.nickName = clotheInfo.getNickName();
    }

    @PersistenceConstructor
    private ClotheDocument(ObjectId clotheId, String uuid, Integer type, Integer pattern,
                           List<String> colors, List<String> features, String nickName,
                           String image, List<String> careInfos, LocalDateTime createdAt, LocalDateTime updatedAt, DocumentStatus status) {
        this.clotheId = clotheId; this.uuid = uuid;
        this.type = type; this.pattern = pattern;
        this.colors = colors; this.features = features;
        this.nickName = nickName; this.image = image;
        this.careInfos = careInfos; this.createdAt = createdAt;
        this.updatedAt = updatedAt; this.status = status;
    }

    public void addCareInfos(List<String> careInfos) {
        this.careInfos = careInfos;
    }

    public enum DocumentStatus {
        ACTIVE, INACTIVE;
    }
}
