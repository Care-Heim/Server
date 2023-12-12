package com.spring.careHeim.domain.clothes.model;

public enum ClotheStatus {
    CLOTHE_NOT_ENROLLED(0),
    ENROLLED(1),
    CAREINFO_NOT_ENROLLED(2),
    DUPLICATED_CLOTHE(3);

    int number;

    ClotheStatus(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
