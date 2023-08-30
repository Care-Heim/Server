package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.domain.clothes.document.ClotheDocument;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClotheDocumentRepository extends MongoRepository<ClotheDocument, String> {
    @Query(value = "{ 'userId': :#{#userID}," +
            "'clothes': " +
            "{ $elemMatch: { 'type': :#{#type}, 'pattern': :#{#ptn}, " +
            "'colors': { $all: :#{#colors} }, 'features': { $all: :#{#features} }, " +
            "'nickName': :#{#nickName} }" +
            "}}")
    int countByUuidAndTypeAndPtnAndColorsAndFeaturesAndNickName(@Param("userID") String userId,
                                                                  @Param("type") int type,
                                                                  @Param("ptn") int ptn,
                                                                  @Param("colors") List<String> colors,
                                                                  @Param("features") List<String> features,
                                                                  @Param("nickName") String nickName);
}
