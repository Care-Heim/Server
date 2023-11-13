package com.spring.careHeim.domain.clothes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClotheResponse {
    private String clotheId;
    private int type;
    private int ptn;
    private List<String> colors;
    private List<String> features;
    private String nickname;
}