package ru.otus.l12.homework.service.impl;

import java.util.List;
import ru.otus.l12.homework.model.BanknoteEntity;
import ru.otus.l12.homework.service.AtmBanknotesCellService;
import ru.otus.l12.homework.service.AtmOnlyPutService;
import ru.otus.l12.homework.service.AtmOnlyWithdrawService;

public class AtmSmartServiceImpl implements AtmOnlyPutService, AtmOnlyWithdrawService {

    private final AtmBanknotesCellService banknotesCellService;

    public AtmSmartServiceImpl() {
        this.banknotesCellService = new AtmBanknotesCellService();
    }

    @Override
    public void putBanknotes(List<BanknoteEntity> banknotesEntities) {
        banknotesCellService.putBanknotesToAtmCell(banknotesEntities);
    }

    @Override
    public List<BanknoteEntity> withDrawBanknotes(int cash) {
        return banknotesCellService.withDrawBanknotes(cash);
    }

    @Override
    public int viewLeftOversBanknotes() {
        return banknotesCellService.viewLeftOversBanknotes();
    }
}
