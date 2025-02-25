package ru.otus.l12.homework.model;

import lombok.Builder;
import lombok.Data;
import ru.otus.l12.homework.utils.BanknotesDenominationEnum;

@Data
@Builder
public class BanknoteEntity {
    BanknotesDenominationEnum denomination;
    int countDenomination;

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Banknote: ")
                .append("denomination - ")
                .append(denomination.getDenomination())
                .append(", ")
                .append("count - ")
                .append(countDenomination);
        return stringBuilder.toString();
    }
}
