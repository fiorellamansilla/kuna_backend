package com.kuna_backend.repositories;


import com.kuna_backend.models.Cart;
import com.kuna_backend.models.Client;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findAllByClientOrderByCreatedAtDesc(Client client);

    List<Cart> deleteByClient(Client client);

}

