package com.kuna_backend.services;

import com.kuna_backend.dtos.shipping.ShippingDetailDto;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.ShippingDetail;
import com.kuna_backend.repositories.ShippingDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShippingDetailService {

    @Autowired
    private ShippingDetailRepository shippingDetailRepository;

    public ShippingDetail addShippingDetail(ShippingDetailDto shippingDetailDto, Client client) {

        ShippingDetail shippingDetail = new ShippingDetail();

        shippingDetail.setFullName(shippingDetailDto.getFullName());
        shippingDetail.setAddress(shippingDetailDto.getAddress());
        shippingDetail.setCity(shippingDetailDto.getCity());
        shippingDetail.setZipCode(shippingDetailDto.getZipCode());
        shippingDetail.setCountry(shippingDetailDto.getCountry());
        shippingDetail.setPhone(shippingDetailDto.getPhone());
        shippingDetail.setClient(client);

        return shippingDetailRepository.save(shippingDetail);

    }

    public List<ShippingDetail> getAllShippingDetails() {
        return (List<ShippingDetail>) shippingDetailRepository.findAll();
    }

    public ShippingDetail getShippingDetail (long id) throws ClassNotFoundException {

        Optional<ShippingDetail> shippingDetail = shippingDetailRepository.findById(id);

        if (shippingDetail.isPresent()) {
            return shippingDetail.get();
        }
        throw new ClassNotFoundException("Shipping detail not found");
    }

}
