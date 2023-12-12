package com.spring.careHeim.domain.clothes.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ClotheResponse extends ClotheInfo{
    private String clotheId;
    private String nickname;
    private boolean canDetectStain;
    private Boolean hasStain;
    private int status;
    private List<String> careInfos;

    public boolean isCanDetectStain() {
        this.canDetectStain = true;

        if(this.getPtn() == 0 || this.getPtn() == 4 || this.getPtn() == 5 || this.getPtn() == 8 || this.getPtn() == 10 ) {
            this.canDetectStain = false;
        }

        return this.canDetectStain;
    }
}