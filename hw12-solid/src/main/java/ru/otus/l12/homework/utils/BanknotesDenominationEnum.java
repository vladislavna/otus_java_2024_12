package ru.otus.l12.homework.utils;

import java.util.Arrays;
import java.util.Comparator;

public enum BanknotesDenominationEnum {
    DENOMINATION_10(10),
    DENOMINATION_50(50),
    DENOMINATION_100(100),
    DENOMINATION_200(200),
    DENOMINATION_500(500);

    private final int denomination;

    BanknotesDenominationEnum(int denomination) {
        this.denomination = denomination;
    }

    public int getDenomination() {
        return denomination;
    }

    public static int getMinDenomination() {
        return Arrays.stream(BanknotesDenominationEnum.values())
                .min(Comparator.naturalOrder())
                .map(BanknotesDenominationEnum::getDenomination)
                .orElse(null);
    }
}
