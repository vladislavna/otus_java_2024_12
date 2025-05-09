package ru.otus.l12.homework.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.random.RandomGenerator;
import ru.otus.l12.homework.exception.MinCheckException;
import ru.otus.l12.homework.exception.WrongDemException;
import ru.otus.l12.homework.model.BanknoteEntity;

public class AtmUtils {

    public void validationBeforeWithDraw(int cash) {
        int minDenomination = BanknotesDenominationEnum.getMinDenomination();
        if (cash < minDenomination) {
            throw new MinCheckException(minDenomination);
        }
        if (cash % minDenomination != 0) {
            throw new WrongDemException(cash);
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
        return RandomGenerator.getDefault().ints(0, 5).findAny().getAsInt();
    }
}
