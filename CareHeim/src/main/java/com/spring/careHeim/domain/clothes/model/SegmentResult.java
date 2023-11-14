package com.spring.careHeim.domain.clothes.model;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Builder
public class SegmentResult {
    private String fileName;
    private String imgUrl;
    private String jsonUrl;
}
