package com.spring.careHeim.domain.clothes.model;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
public class ClotheRequest extends ClotheInfo{
    private String nickname;
}