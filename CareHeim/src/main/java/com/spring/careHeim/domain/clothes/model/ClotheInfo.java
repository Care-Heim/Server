package com.spring.careHeim.domain.clothes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClotheInfo {
    private int type;
    private int ptn;
    private List<String> colors;
    private List<String> features;
    @JsonIgnore
    private byte[] img;
}
