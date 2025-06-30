package ru.otus.hw.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.otus.hw.model.Client;
import ru.otus.hw.service.ServiceClient;

@RestController
@RequestMapping("/api/v1/client")
@AllArgsConstructor
public class ClientRestController {
    private final ServiceClient clientService;

    @PostMapping
    public Client saveClient(@RequestBody Client clientRequest) {
        return clientService.saveClient(clientRequest);
    }

    @GetMapping("/{id}")
    public Client getClientById(@PathVariable long id) {
        return clientService.getClient(id);
    }
}
