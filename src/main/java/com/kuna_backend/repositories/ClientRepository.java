package com.kuna_backend.repositories;

import com.kuna_backend.models.Client;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends CrudRepository<Client, Integer> {

    Client findByEmail (String email);
}
