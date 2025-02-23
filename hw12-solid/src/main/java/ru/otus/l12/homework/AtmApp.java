package ru.otus.l12.homework;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.l12.homework.model.BanknoteEntity;
import ru.otus.l12.homework.service.impl.AtmSmartServiceImpl;
import ru.otus.l12.homework.utils.AtmUtils;

public class AtmApp {
    private static final Logger logger = LoggerFactory.getLogger(AtmApp.class);

    public static void main(String[] args) {

        AtmUtils atmUtils = new AtmUtils();
        AtmSmartServiceImpl atmService = new AtmSmartServiceImpl();
        List<BanknoteEntity> banknotesEntities = atmUtils.fillInBanknotesEntities();
        atmService.putBanknotes(banknotesEntities);
        logger.info("LeftOversBanknotes Before:\n");
        for (BanknoteEntity banknoteEntity : banknotesEntities) {
            logger.info(banknoteEntity.toString());
        }
        logger.info("LeftOversSum Before: {}", atmService.viewLeftOversBanknotes());
        int cash = 150;
        logger.info("Cash: {}", cash);
        atmUtils.validationBeforeWithDraw(cash);
        List<BanknoteEntity> banknoteEntities = atmService.withDrawBanknotes(cash);
        logger.info("WithDrawCash:\n");
        for (BanknoteEntity banknoteEntity : banknoteEntities) {
            logger.info(banknoteEntity.toString());
        }
        logger.info("LeftOversBanknotes After: {}\n", atmService.viewLeftOversBanknotes());
    }
}
