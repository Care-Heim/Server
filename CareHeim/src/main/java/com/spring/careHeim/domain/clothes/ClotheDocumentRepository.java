package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.domain.clothes.document.ClotheDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClotheDocumentRepository extends MongoRepository<ClotheDocument, ObjectId> {
    @Query(value = "{ 'uuid': :#{#uuid}, " +
            "'type': :#{#type}, 'pattern': :#{#ptn}, " +
            "'colors': { $all: :#{#colors} }, " +
            "'nickName': :#{#nickName} }", count = true)
    Integer countByUuidAndTypeAndPtnAndColorsAndNickName(@Param("uuid") String uuid,
                                                                @Param("type") int type,
                                                                @Param("ptn") int ptn,
                                                                @Param("colors") String[] colors,
                                                                @Param("nickName") String nickName);
    @Query(value = "{ 'uuid': :#{#uuid}," +
            "'type': :#{#type}, 'pattern': :#{#ptn}, " +
            "'colors': { $all: :#{#colors} }, 'features': { $all: :#{#features} }, " +
            "'nickName': :#{#nickName} }", count = true)
    Integer countByUuidAndTypeAndPtnAndColorsAndFeaturesAndNickName(@Param("uuid") String uuid,
                                                                  @Param("type") int type,
                                                                  @Param("ptn") int ptn,
                                                                  @Param("colors") String[] colors,
                                                                  @Param("features") String[] features,
                                                                  @Param("nickName") String nickName);
}
