package ru.otus.hw.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import ru.otus.hw.exceptions.DataJdbcException;
import ru.otus.hw.model.Client;
import ru.otus.hw.model.Phone;
import ru.otus.hw.repository.ClientRepository;
import ru.otus.hw.repository.PhoneRepository;
import ru.otus.hw.service.ServiceClient;

@Service
@RequiredArgsConstructor
public class ServiceClientImpl implements ServiceClient {
    private static final Logger log = LoggerFactory.getLogger(ServiceClientImpl.class);

    private final ClientRepository clientRepository;
    private final PhoneRepository phoneRepository;

    @Override
    public Client saveClient(Client client) {
        Client savedClient = clientRepository.save(new Client(null, client.name(), client.address(), null));
        List<Phone> phones = client.phones().stream()
                .map(phone -> new Phone(null, phone.number(), savedClient.id()))
                .toList();
        if (!CollectionUtils.isEmpty(phones)) {
            phoneRepository.saveAll(phones);
        }
        return clientRepository.findById(savedClient.id()).orElseThrow();
    }

    @Override
    public Client getClient(long id) {
        return clientRepository.findById(id).orElseThrow(() -> new DataJdbcException("Клиент не был найден id=" + id));
    }

    @Override
    public List<Client> findAll() {
        return clientRepository.findAll();
    }
}
