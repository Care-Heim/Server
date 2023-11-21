package com.spring.careHeim.domain.clothes.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum Pattern {
    ANIMAL(0),
    CHECK(1),
    CHEVRON(2),
    DIAMOND(3),
    FLORAL(4),
    GRAPHHIC(5),
    LETTERING(6),
    PLAIN(7),
    POLKA_DOTS(8),
    STRIPES(9),
    NULL(10);

    private final int number;

    Pattern(int number) {
        this.number = number;
    }

    @JsonValue
    public int getNumber() {
        return number;
    }

    @JsonCreator
    public static Pattern fromNumber(int number) {
        return Arrays.stream(Pattern.values())
                .filter(pattern -> pattern.number == number)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Invalid num : " + number));
    }
}
