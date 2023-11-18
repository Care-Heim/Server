package com.spring.careHeim.domain.clothes.model;

import lombok.*;
import org.opencv.core.Point;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class SegClothe {
    private ClotheType type;
    private List<int[]> coordinates;
}
