package com.spring.careHeim;

import com.spring.careHeim.domain.clothes.ClotheDocumentRepository;
import com.spring.careHeim.domain.clothes.document.ClotheDocument;
import com.spring.careHeim.domain.clothes.model.ClotheInfo;
import com.spring.careHeim.domain.users.UserRepository;
import com.spring.careHeim.domain.users.entity.User;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

@SpringBootTest
class CareHeimServerApplicationTests {
    @Autowired
    ClotheDocumentRepository clotheDocumentRepository;

    @Test
    void findClothe() {
        Optional<ClotheDocument> clotheDocument = clotheDocumentRepository.findById(new ObjectId("64f048a2d33f0e63636f9d22"));

        if(clotheDocument.isPresent()) {
            System.out.println();
            System.out.println("******************************************");
            System.out.println("not null");
            System.out.println(clotheDocument.get().getClotheId());
            System.out.println("******************************************");
        } else {
            System.out.println();
            System.out.println("******************************************");
            System.out.println("null");
            System.out.println("******************************************");
        }
    }
}
