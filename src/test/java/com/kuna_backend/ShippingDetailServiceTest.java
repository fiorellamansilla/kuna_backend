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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ShippingDetailServiceTest {
    @Mock
    private ShippingDetailRepository shippingDetailRepository;

    @InjectMocks
    private ShippingDetailService shippingDetailService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddShippingDetail() {

        ShippingDetailDto shippingDetailDto = new ShippingDetailDto();
        shippingDetailDto.setFullName("John Doe");
        shippingDetailDto.setAddress("123 Main St");
        shippingDetailDto.setCity("Lima");
        shippingDetailDto.setZipCode("12345");
        shippingDetailDto.setCountry("Peru");
        shippingDetailDto.setPhone("456-7890");

        Client client = new Client();

        ShippingDetail shippingDetail = new ShippingDetail();
        shippingDetail.setFullName("John Doe");
        shippingDetail.setAddress("123 Main St");
        shippingDetail.setCity("Lima");
        shippingDetail.setZipCode("12345");
        shippingDetail.setCountry("Peru");
        shippingDetail.setPhone("456-7890");
        shippingDetail.setClient(client);

        when(shippingDetailRepository.save(any(ShippingDetail.class))).thenReturn(shippingDetail);

        ShippingDetail result = shippingDetailService.addShippingDetail(shippingDetailDto, client);

        verify(shippingDetailRepository).save(any(ShippingDetail.class));

        assertEquals("John Doe", result.getFullName());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("Lima", result.getCity());
        assertEquals("12345", result.getZipCode());
        assertEquals("Peru", result.getCountry());
        assertEquals("456-7890", result.getPhone());
        assertEquals(client, result.getClient());
    }

    @Test
    public void testGetAllShippingDetails() {

        List<ShippingDetail> shippingDetails = new ArrayList<>();
        when(shippingDetailRepository.findAll()).thenReturn(shippingDetails);

        List<ShippingDetail> result = shippingDetailService.getAllShippingDetails();

        verify(shippingDetailRepository).findAll();

        assertEquals(shippingDetails, result);
    }

    @Test
    public void testGetShippingDetail_ValidId() throws ClassNotFoundException {

        ShippingDetail shippingDetail = new ShippingDetail();
        int shippingDetailId = 1;

        when(shippingDetailRepository.findById(shippingDetailId)).thenReturn(Optional.of(shippingDetail));

        ShippingDetail result = shippingDetailService.getShippingDetail(shippingDetailId);

        verify(shippingDetailRepository).findById(shippingDetailId);

        assertEquals(shippingDetail, result);
    }

    @Test
    public void testGetShippingDetail_InvalidId() {

        int invalidId = 999;

        when(shippingDetailRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(ClassNotFoundException.class, () -> shippingDetailService.getShippingDetail(invalidId));
    }

}
