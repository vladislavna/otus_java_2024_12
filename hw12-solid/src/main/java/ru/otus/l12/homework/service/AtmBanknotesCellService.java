package ru.otus.l12.homework.service;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import ru.otus.l12.homework.exception.NotEnoughCashException;
import ru.otus.l12.homework.model.BanknoteEntity;
import ru.otus.l12.homework.utils.BanknotesDenominationEnum;
import ru.otus.l12.homework.utils.ErrorsEnum;

public class AtmBanknotesCellService {

    private Map<BanknotesDenominationEnum, Integer> availableBanknotes;

    public AtmBanknotesCellService() {
        this.availableBanknotes = new HashMap<>();
    }

    public void putBanknotesToAtmCell(List<BanknoteEntity> banknotesEntities) {
        for (BanknoteEntity banknotes : banknotesEntities) {
            availableBanknotes.merge(banknotes.getDenomination(), banknotes.getCountDenomination(), Integer::sum);
        }
    }

    public List<BanknoteEntity> withDrawBanknotes(int cash) {
        List<BanknoteEntity> banknotesEntity = new ArrayList<>();
        Map<BanknotesDenominationEnum, Integer> availableBanknotesCopy = new HashMap<>();
        availableBanknotesCopy.putAll(availableBanknotes);
        Supplier<TreeMap<BanknotesDenominationEnum, Integer>> treeReverseSupplier =
                () -> new TreeMap<>((b1, b2) -> b2.getDenomination() - b1.getDenomination());
        availableBanknotes = availableBanknotes.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey, Map.Entry::getValue, (key1, key2) -> key1, treeReverseSupplier));
        for (Map.Entry<BanknotesDenominationEnum, Integer> availableBanknote : availableBanknotes.entrySet()) {
            if (cash == 0) {
                break;
            }
            BanknotesDenominationEnum availableBanknoteDem = availableBanknote.getKey();
            Integer availableBanknoteCount = availableBanknote.getValue();
            Integer requiredAmount = cash / availableBanknoteDem.getDenomination();
            if (requiredAmount < 1) {
                continue;
            }
            if (availableBanknoteCount < requiredAmount) {
                cash -= availableBanknoteDem.getDenomination() * availableBanknoteCount;
                banknotesEntity.add(BanknoteEntity.builder()
                        .countDenomination(availableBanknoteCount)
                        .denomination(availableBanknoteDem)
                        .build());
                availableBanknotesCopy.remove(availableBanknote.getKey());
            } else {
                cash -= availableBanknoteDem.getDenomination() * requiredAmount;
                banknotesEntity.add(BanknoteEntity.builder()
                        .countDenomination(requiredAmount)
                        .denomination(availableBanknoteDem)
                        .build());
                availableBanknotesCopy.put(availableBanknote.getKey(), availableBanknoteCount - requiredAmount);
            }
        }

        if (cash > 0) {
            throw new NotEnoughCashException(ErrorsEnum.ERR_NOT_ENOUGH.getMessage());
        }
        availableBanknotes = availableBanknotesCopy;
        return banknotesEntity;
    }

    public int viewLeftOversBanknotes() {
        return availableBanknotes.entrySet().stream()
                .mapToInt(e -> (e.getKey().getDenomination() * e.getValue()))
                .sum();
    }
}
