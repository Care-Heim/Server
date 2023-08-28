package com.spring.careHeim.domain.clothes;

import com.spring.careHeim.domain.clothes.document.Clothe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ClotheRepository extends MongoRepository<Clothe, String> {

}
