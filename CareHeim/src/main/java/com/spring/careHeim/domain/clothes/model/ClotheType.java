package com.spring.careHeim.domain.clothes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ClotheType {
    SHORT_SLEEVE_TOP(0),
    LONG_SLEEVE_TOP(1),
    SHORT_SLEEVE_OUTWEAR(2),
    LONG_SLEEVE_OUTWEAR(3),
    VEST(4),
    SLING(5),
    SHORTS(6),
    TROUSERS(7),
    SKIRT(8),
    SHORT_SLEEVE_DRESS(9),
    LONG_SLEEVE_DRESS(10),
    NO_SLEEVE_DRESS(11);

    private final int number;

    ClotheType(int number) {
        this.number = number;
    }

    @JsonValue
    public int getNumber() {
        return number;
    }

    @JsonCreator
    public static ClotheType fromNumber(int number) {
        return Arrays.stream(ClotheType.values())
                .filter(clotheType -> clotheType.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid num : " + number));
    }
}
