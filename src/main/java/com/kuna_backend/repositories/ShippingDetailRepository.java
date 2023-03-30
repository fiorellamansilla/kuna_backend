package com.kuna_backend.repositories;

import com.kuna_backend.models.ShippingDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingDetailRepository extends JpaRepository <ShippingDetail, Integer> {
}
