package ru.otus.cachehw;

import lombok.extern.slf4j.Slf4j;
import ru.otus.crm.model.Client;

@Slf4j
public class HwListenerImpl implements HwListener<String, Client> {

    @Override
    public void notify(String key, Client value, String action) {
        log.info("key:{}, value:{}, action: {}", key, value, action);
    }
}
