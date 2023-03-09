package com.kuna_backend.repositories;

import com.kuna_backend.models.AuthenticationToken;
import com.kuna_backend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository <AuthenticationToken, Integer> {
    AuthenticationToken findTokenByClient(Client client);
    AuthenticationToken findTokenByToken(String token);
}
