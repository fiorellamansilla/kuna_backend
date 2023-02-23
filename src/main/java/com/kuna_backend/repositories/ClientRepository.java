package com.kuna_backend.repositories;

import com.kuna_backend.models.Client;
import org.springframework.data.repository.CrudRepository;

public interface ClientRepository extends CrudRepository<Client, Integer> {
}
