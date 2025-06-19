package com.hotel.service;

import com.hotel.dao.repository.AmenityRepository;
import com.hotel.dao.repository.HotelRepository;
import com.hotel.model.dto.*;
import com.hotel.model.entity.Amenity;
import com.hotel.model.entity.Hotel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class HotelServiceTest {

    @Mock private HotelRepository hotelRepository;
    @Mock private AmenityRepository amenityRepository;
    @Mock private HotelMapper hotelMapper;

    @InjectMocks private HotelService hotelService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllHotelsBrief_shouldReturnDtos() {
        Hotel hotel = new Hotel();
        HotelDTO<String> dto = new HotelDTO<>();

        when(hotelRepository.findAll()).thenReturn(List.of(hotel));
        when(hotelMapper.toBriefDto(hotel)).thenReturn(dto);

        List<HotelDTO<String>> result = hotelService.getAllHotelsBrief();

        assertEquals(1, result.size());
        verify(hotelRepository).findAll();
    }

    @Test
    void getHotelDetail_shouldReturnOptionalDto() {
        Hotel hotel = new Hotel();
        HotelDTO<AddressDTO> dto = new HotelDTO<>();

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(hotelMapper.toDetailDto(hotel)).thenReturn(dto);

        Optional<HotelDTO<AddressDTO>> result = hotelService.getHotelDetail(1L);

        assertTrue(result.isPresent());
        verify(hotelRepository).findById(1L);
    }

    @Test
    void createHotel_shouldReturnSavedDto() {
        HotelCreateRequest request = new HotelCreateRequest();
        Hotel hotel = new Hotel();
        Hotel saved = new Hotel();
        HotelDTO<AddressDTO> dto = new HotelDTO<>();

        when(hotelMapper.toEntity(request)).thenReturn(hotel);
        when(hotelRepository.save(hotel)).thenReturn(saved);
        when(hotelMapper.toDetailDto(saved)).thenReturn(dto);

        HotelDTO<AddressDTO> result = hotelService.createHotel(request);

        assertNotNull(result);
        verify(hotelRepository).save(hotel);
    }

    @Test
    void addAmenitiesToHotel_shouldAddAndReturnAmenities() {
        Long hotelId = 1L;
        String amenityName = "WiFi";

        Amenity existingAmenity = Amenity.builder().name(amenityName).build();
        Hotel hotel = new Hotel();
        hotel.setAmenities(new HashSet<>());

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByNameIgnoreCase(amenityName)).thenReturn(Optional.of(existingAmenity));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Optional<List<String>> result = hotelService.addAmenitiesToHotel(hotelId, List.of(amenityName));

        assertTrue(result.isPresent());
        assertTrue(result.get().contains(amenityName));
        verify(hotelRepository).save(hotel);
    }

    @Test
    void addAmenitiesToHotel_shouldReturnEmptyIfHotelNotFound() {
        Long hotelId = 999L;

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.empty());

        Optional<List<String>> result = hotelService.addAmenitiesToHotel(hotelId, List.of("WiFi"));

        assertTrue(result.isEmpty());
        verify(hotelRepository, never()).save(any());
    }

    @Test
    void addAmenitiesToHotel_shouldCreateNewAmenityIfNotFound() {
        Long hotelId = 2L;
        String amenityName = "Gym";

        Hotel hotel = new Hotel();
        hotel.setAmenities(new HashSet<>());

        when(hotelRepository.findById(hotelId)).thenReturn(Optional.of(hotel));
        when(amenityRepository.findByNameIgnoreCase(amenityName)).thenReturn(Optional.empty());
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Optional<List<String>> result = hotelService.addAmenitiesToHotel(hotelId, List.of(amenityName));

        assertTrue(result.isPresent());
        assertTrue(result.get().contains("Gym"));
    }
}
