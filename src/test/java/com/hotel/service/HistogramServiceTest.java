package com.hotel.service;

import com.hotel.dao.repository.HotelRepository;
import com.hotel.model.entity.Address;
import com.hotel.model.entity.Amenity;
import com.hotel.model.entity.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HistogramServiceTest {

    @Mock
    private HotelRepository hotelRepository;

    @InjectMocks
    private HistogramService histogramService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getHistogram_city_shouldReturnMap() {
        Address address = new Address();
        address.setCity("Berlin");

        Hotel hotel = new Hotel();
        hotel.setAddress(address);

        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        Map<String, Long> result = histogramService.getHistogram("city");

        assertEquals(1, result.size());
        assertEquals(1L, result.get("Berlin"));
    }

    @Test
    void getHistogram_brand_shouldReturnMap() {
        Hotel hotel = new Hotel();
        hotel.setBrand("Hilton");

        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        Map<String, Long> result = histogramService.getHistogram("brand");

        assertEquals(1L, result.get("Hilton"));
    }

    @Test
    void getHistogram_amenities_shouldReturnMap() {
        Amenity amenity = Amenity.builder().name("WiFi").build();
        Hotel hotel = new Hotel();
        hotel.setAmenities(Set.of(amenity));

        when(hotelRepository.findAll()).thenReturn(List.of(hotel));

        Map<String, Long> result = histogramService.getHistogram("amenities");

        assertEquals(1L, result.get("WiFi"));
    }

    @Test
    void getHistogram_invalid_shouldThrowException() {
        assertThrows(IllegalArgumentException.class,
                () -> histogramService.getHistogram("invalid"));
    }
}
