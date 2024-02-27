package com.kuna_backend;

import com.kuna_backend.dtos.shipping.ShippingDetailDto;
import com.kuna_backend.models.Client;
import com.kuna_backend.models.ShippingDetail;
import com.kuna_backend.repositories.ShippingDetailRepository;
import com.kuna_backend.services.ShippingDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShippingDetailServiceTest {
    @Mock
    private ShippingDetailRepository shippingDetailRepository;

    @Mock
    private ShippingDetail existingShippingDetail;

    @InjectMocks
    private ShippingDetailService shippingDetailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void updateShippingDetailForExistingShippingDetail() {

        Client client = new Client();
        client.setShippingDetail(existingShippingDetail);

        Long id = 1L;
        ShippingDetailDto shippingDetailDto = new ShippingDetailDto("John Doe","123 Main St","Lima",
                "12345","Peru","456-7890");

        when(client.getShippingDetail().getId()).thenReturn(id);
        when(shippingDetailRepository.findById(id)).thenReturn(java.util.Optional.of(existingShippingDetail));

        shippingDetailService.addShippingDetail(shippingDetailDto, client);

        verify(shippingDetailRepository).save(existingShippingDetail);
        verify(existingShippingDetail).setFullName(shippingDetailDto.getFullName());
        verify(existingShippingDetail).setAddress(shippingDetailDto.getAddress());
        verify(existingShippingDetail).setCity(shippingDetailDto.getCity());
        verify(existingShippingDetail).setZipCode(shippingDetailDto.getZipCode());
        verify(existingShippingDetail).setCountry(shippingDetailDto.getCountry());
        verify(existingShippingDetail).setPhone(shippingDetailDto.getPhone());
    }
    @Test
    public void addShippingDetailForNewShippingDetail() {

        Client client = new Client();
        client.setShippingDetail(existingShippingDetail);

        Long id = 1L;
        ShippingDetailDto shippingDetailDto = new ShippingDetailDto("John Doe","123 Main St","Lima",
                "12345","Peru","456-7890");

        when(client.getShippingDetail().getId()).thenReturn(id);
        when(shippingDetailRepository.findById(id)).thenReturn(java.util.Optional.of(existingShippingDetail));

        shippingDetailService.addShippingDetail(shippingDetailDto, client);

        verify(shippingDetailRepository, times(1)).save(any());
    }


    @Test
    public void getAllShippingDetails() {

        List<ShippingDetail> shippingDetails = new ArrayList<>();
        when(shippingDetailRepository.findAll()).thenReturn(shippingDetails);

        List<ShippingDetail> result = shippingDetailService.getAllShippingDetails();

        verify(shippingDetailRepository).findAll();

        assertEquals(shippingDetails, result);
    }

    @Test
    public void getShippingDetailWithValidId() {

        ShippingDetail shippingDetail = new ShippingDetail();
        Long shippingDetailId = 1L;

        Client client = new Client();
        client.setShippingDetail(shippingDetail);

        when(shippingDetailRepository.findById(client.getShippingDetail().getId())).thenReturn(Optional.of(shippingDetail));

        Optional<ShippingDetail> result = shippingDetailService.getShippingDetail(client);

        verify(shippingDetailRepository).findById(client.getShippingDetail().getId());
        assertEquals(shippingDetailRepository.findById(client.getShippingDetail().getId()), result);
    }

    @Test
    public void getShippingDetailWithInvalidId() {

        Long invalidId = 999L;

        when(shippingDetailRepository.findById(invalidId)).thenReturn(Optional.empty());
    }

}
