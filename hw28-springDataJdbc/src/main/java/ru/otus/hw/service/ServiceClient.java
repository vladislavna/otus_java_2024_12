package ru.otus.hw.service;

import java.util.List;
import ru.otus.hw.model.Client;

public interface ServiceClient {

    Client saveClient(Client client);

    Client getClient(long id);

    List<Client> findAll();
}
