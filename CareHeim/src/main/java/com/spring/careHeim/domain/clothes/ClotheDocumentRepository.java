package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.domain.clothes.document.ClotheDocument;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClotheDocumentRepository extends MongoRepository<ClotheDocument, ObjectId> {
    @Query(value = "{ 'uuid': :#{#uuid}, " +
            "'type': :#{#type}, 'pattern': :#{#ptn}, " +
            "'colors': { $all: :#{#colors} }, " +
            "'nickname': :#{#nickName} }", count = true)
    Integer countByUuidAndTypeAndPtnAndColorsAndNickName(@Param("uuid") String uuid,
                                                                @Param("type") int type,
                                                                @Param("ptn") int ptn,
                                                                @Param("colors") String[] colors,
                                                                @Param("nickName") String nickName);
    @Query(value = "{ 'uuid': :#{#uuid}," +
            "'type': :#{#type}, 'pattern': :#{#ptn}, " +
            "'colors': { $all: :#{#colors} }, 'features': { $all: :#{#features} }, " +
            "'nickname': :#{#nickName} }", count = true)
    Integer countByUuidAndTypeAndPtnAndColorsAndFeaturesAndNickName(@Param("uuid") String uuid,
                                                                  @Param("type") int type,
                                                                  @Param("ptn") int ptn,
                                                                  @Param("colors") String[] colors,
                                                                  @Param("features") String[] features,
                                                                  @Param("nickName") String nickName);

    @Aggregation(
            pipeline = {
                    "{ $match: {'uuid': :#{#uuid}, status: 'ACTIVE'} }",
                    "{ $sort: {'createdAt' :  -1 } }",
                    "{ $limit: 1 }"
            }
    )
    ClotheDocument findRecentClothe(@Param("uuid") String uuid);

    @Aggregation(
            pipeline = {
                    "{ $match:  {'uuid': :#{#uuid}, status: 'ACTIVE', " +
                            "'type': :#{#type}, " +
                            "'pattern': :#{#ptn}, " +
                            "'colors': :#{#colors}, " +
                            "'features': :#{#features}, " +
                            "'nickname': :#{#nickname} } }"
            }
    )
    List<ClotheDocument> findClothes(@Param("uuid") String uuid,
                              @Param("type") int type,
                              @Param("ptn") int ptn,
                              @Param("colors") String[] colors,
                              @Param("features") String[] features,
                              @Param("nickname") String nickname);

}
