package com.hotel.service;

import com.hotel.dao.repository.HotelRepository;
import com.hotel.dao.specification.HotelSpecification;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.entity.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SearchServiceTest {

    @Mock private HotelRepository hotelRepository;
    @Mock private HotelMapper hotelMapper;
    @Mock private HotelSpecification hotelSpecification;

    @InjectMocks private SearchService searchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void searchHotels_shouldReturnMappedDTOs() {
        String name = "Test";
        String brand = "Brand";
        String city = "City";
        String country = "Country";
        String amenity = "WiFi";

        Hotel hotel = new Hotel();
        HotelDTO<String> dto = new HotelDTO<>();

        when(hotelSpecification.nameContains(name)).thenReturn((root, query, cb) -> null);
        when(hotelSpecification.brandContains(brand)).thenReturn((root, query, cb) -> null);
        when(hotelSpecification.cityContains(city)).thenReturn((root, query, cb) -> null);
        when(hotelSpecification.countryContains(country)).thenReturn((root, query, cb) -> null);
        when(hotelSpecification.hasAmenity(amenity)).thenReturn((root, query, cb) -> null);

        when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));
        when(hotelMapper.toBriefDto(hotel)).thenReturn(dto);

        List<HotelDTO<String>> result = searchService.searchHotels(name, brand, city, country, amenity);

        assertEquals(1, result.size());
        verify(hotelRepository).findAll(any(Specification.class));
    }

}
