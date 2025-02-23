package ru.otus.l12.homework.service;

import java.util.List;
import ru.otus.l12.homework.model.BanknoteEntity;

public interface AtmOnlyWithdrawService {
    public List<BanknoteEntity> withDrawBanknotes(int cash);

    public int viewLeftOversBanknotes();
}
