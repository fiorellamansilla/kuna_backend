package com.kuna_backend.repositories;

import com.kuna_backend.models.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository <User, Integer> {
}
