package com.spring.careHeim.domain.clothes.model;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class ClotheInfo {
    private int type;
    private int ptn;
    private List<String> colors;
    private List<String> features;
    private String nickName;
}