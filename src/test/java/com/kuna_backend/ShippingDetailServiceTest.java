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

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        shippingDetailDto.setCity("City");
        shippingDetailDto.setZipCode("12345");
        shippingDetailDto.setCountry("Country");
        shippingDetailDto.setPhone("123-456-7890");

        Client client = new Client();

        ShippingDetail shippingDetail = new ShippingDetail();
        shippingDetail.setFullName("John Doe");
        shippingDetail.setAddress("123 Main St");
        shippingDetail.setCity("City");
        shippingDetail.setZipCode("12345");
        shippingDetail.setCountry("Country");
        shippingDetail.setPhone("123-456-7890");
        shippingDetail.setClient(client);

        when(shippingDetailRepository.save(any(ShippingDetail.class))).thenReturn(shippingDetail);

        ShippingDetail result = shippingDetailService.addShippingDetail(shippingDetailDto, client);

        verify(shippingDetailRepository).save(any(ShippingDetail.class));

        assertEquals("John Doe", result.getFullName());
        assertEquals("123 Main St", result.getAddress());
        assertEquals("City", result.getCity());
        assertEquals("12345", result.getZipCode());
        assertEquals("Country", result.getCountry());
        assertEquals("123-456-7890", result.getPhone());
        assertEquals(client, result.getClient());

    }

}
