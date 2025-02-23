package ru.otus.l12.homework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import ru.otus.l12.homework.exception.MinCheckException;
import ru.otus.l12.homework.model.BanknoteEntity;

public class AtmUtils {

    public void validationBeforeWithDraw(int cash) {
        int minDenomination = BanknotesDenominationEnum.getMinDenomination();
        if (cash < BanknotesDenominationEnum.getMinDenomination()) {
            throw new MinCheckException(ErrorsEnum.ERR_MIN_CHECK.getMessage());
        }
        if (cash % minDenomination != 0) {
            throw new MinCheckException(ErrorsEnum.ERR_WRONG_DEM.getMessage());
        }
    }

    public List<BanknoteEntity> fillInBanknotesEntities() {
        List<BanknoteEntity> banknotesEntities = new ArrayList<>();
        for (BanknotesDenominationEnum value : BanknotesDenominationEnum.values()) {
            Integer randomInt = randomGenerator();
            if (randomInt != 0) {
                banknotesEntities.add(BanknoteEntity.builder()
                    .denomination(value)
                    .countDenomination(randomInt)
                    .build());
            }
        }
        return banknotesEntities;
    }

    protected Integer randomGenerator() {
        return (Integer) RandomGenerator.getDefault().ints(0, 5).findAny().getAsInt();
    }
}
