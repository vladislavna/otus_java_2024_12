package ru.otus.crm.service;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import ru.otus.cachehw.HwCache;
import ru.otus.core.repository.DataTemplate;
import ru.otus.core.sessionmanager.TransactionManager;
import ru.otus.crm.model.Client;

@Slf4j
public class DbServiceClientImpl implements DBServiceClient {

    private final DataTemplate<Client> clientDataTemplate;
    private final TransactionManager transactionManager;
    private final HwCache<String, Client> cache;

    public DbServiceClientImpl(
            TransactionManager transactionManager,
            DataTemplate<Client> clientDataTemplate,
            HwCache<String, Client> cache) {
        this.transactionManager = transactionManager;
        this.clientDataTemplate = clientDataTemplate;
        this.cache = cache;
    }

    @Override
    public Client saveClient(Client client) {
        return transactionManager.doInTransaction(session -> {
            var clientCloned = client.clone();
            if (client.getId() == null) {
                var savedClient = clientDataTemplate.insert(session, clientCloned);
                cache.put(getCacheKey(savedClient), savedClient.clone());
                log.info("created client: {}", clientCloned);
                return savedClient;
            }
            var savedClient = clientDataTemplate.update(session, clientCloned);
            cache.put(getCacheKey(savedClient), savedClient.clone());
            log.info("updated client: {}", savedClient);
            return savedClient;
        });
    }

    @Override
    public Optional<Client> getClient(long id) {
        Optional<Client> client = Optional.ofNullable(cache.get(String.valueOf(id)));
        if (client.isEmpty()) {
            client = transactionManager.doInReadOnlyTransaction(session -> {
                var clientOptional = clientDataTemplate.findById(session, id);
                clientOptional.ifPresent(value -> cache.put(String.valueOf(id), value.clone()));
                log.info("client: {}", clientOptional);
                return clientOptional;
            });
        }
        return client;
    }

    @Override
    public List<Client> findAll() {
        return transactionManager.doInReadOnlyTransaction(session -> {
            var clientList = clientDataTemplate.findAll(session);
            clientList.forEach(client -> cache.put(String.valueOf(client.getId()), client.clone()));
            log.info("clientList:{}", clientList);
            return clientList;
        });
    }

    private String getCacheKey(Client client) {
        return client.getId().toString();
    }
}
