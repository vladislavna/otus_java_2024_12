package ru.otus.l12;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.otus.l12.homework.exception.MinCheckException;
import ru.otus.l12.homework.exception.NotEnoughCashException;
import ru.otus.l12.homework.model.BanknoteEntity;
import ru.otus.l12.homework.service.impl.AtmSmartServiceImpl;
import ru.otus.l12.homework.utils.AtmUtils;
import ru.otus.l12.homework.utils.BanknotesDenominationEnum;

class AtmSmartServiceImplTest {
    private static AtmSmartServiceImpl atmService;
    private static AtmUtils atmUtils;

    @BeforeAll
    static void setUp() {
        List<BanknoteEntity> banknotesEntities = new ArrayList<>();
        banknotesEntities.add(BanknoteEntity.builder()
                .denomination(BanknotesDenominationEnum.DENOMINATION_200)
                .countDenomination(2)
                .build());
        banknotesEntities.add(BanknoteEntity.builder()
                .denomination(BanknotesDenominationEnum.DENOMINATION_50)
                .countDenomination(3)
                .build());
        atmUtils = new AtmUtils();
        atmService = new AtmSmartServiceImpl();
        atmService.putBanknotes(banknotesEntities);
    }

    @Test
    void When_Cash150_Expect_Success() {
        List<BanknoteEntity> banknoteEntitiesExpected = new ArrayList<>();
        banknoteEntitiesExpected.add(BanknoteEntity.builder()
                .denomination(BanknotesDenominationEnum.DENOMINATION_50)
                .countDenomination(3)
                .build());
        int cash = 150;
        List<BanknoteEntity> banknoteEntitiesActual = atmService.withDrawBanknotes(cash);

        assertThat(banknoteEntitiesExpected).isEqualTo(banknoteEntitiesActual);
    }

    @Test
    void When_Cash80_Expect_NotEnoughCashException() {
        int cash = 80;
        Assertions.assertThrows(NotEnoughCashException.class, () -> atmService.withDrawBanknotes(cash));
    }

    @Test
    void When_Cash9_Expect_MinCheckException() {
        int cash = 9;
        Assertions.assertThrows(MinCheckException.class, () -> atmUtils.validationBeforeWithDraw(cash));
    }
}
