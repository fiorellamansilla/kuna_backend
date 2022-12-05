package com.kuna_backend.dao;

import com.kuna_backend.entities.Client;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Component
public class ClientDaoService {

    private static List<Client> clients = new ArrayList<>();

    private static int clientsCount = 0;

    static {
        clients.add(new Client(++clientsCount,
                "fmansilla",
                "1392",
                "Fiorella",
                "Mansilla",
                "Bei der Flottbeker Muhle 3",
                "22607",
                "Hamburg",
                "Germany",
                "0750904871",
                "fiorellamansilla@gmail.com",
                LocalDateTime.now(),
                LocalDateTime.now()));
    };

    // GET all clients Method
    public List<Client> findAll() {
        return clients;
    }

    // GET one client by ID Method
    public Client findOne(int id) {
        Predicate<? super Client> predicate = client -> client.getClient_id().equals(id);
        return clients.stream().filter(predicate).findFirst().orElse(null);
    }

    // POST a client Method
    public Client save(Client client) {
        client.setClient_id(++clientsCount);
        clients.add(client);
        return client;
    }

    // DELETE client Method
    public void deleteById(int id) {
        Predicate<? super Client> predicate = client -> client.getClient_id().equals(id);
        clients.removeIf(predicate);
    }



}
