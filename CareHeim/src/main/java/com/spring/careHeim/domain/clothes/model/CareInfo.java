package com.spring.careHeim.domain.clothes.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class CareInfo {
    String clotheId;
    List<String> careInfos;
}
