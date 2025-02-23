package ru.otus.l12.homework.service;

import java.util.List;
import ru.otus.l12.homework.model.BanknoteEntity;

public interface AtmOnlyPutService {
    void putBanknotes(List<BanknoteEntity> banknotesEntities);
}
