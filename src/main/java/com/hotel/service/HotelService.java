package com.hotel.service;

import com.hotel.dao.repository.AmenityRepository;
import com.hotel.dao.repository.HotelRepository;
import com.hotel.metrics.BookingMetrics;
import com.hotel.model.dto.*;
import com.hotel.model.entity.Amenity;
import com.hotel.model.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HotelService {

    private final HotelRepository hotelRepository;
    private final AmenityRepository amenityRepository;
    private final HotelMapper hotelMapper;
    private final BookingMetrics bookingMetrics;

    public List<HotelDTO<String>> getAllHotelsBrief() {
        bookingMetrics.onBriefRequest(); // todo metrics
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toBriefDto)
                .collect(Collectors.toList());
    }

    public Optional<HotelDTO<AddressDTO>> getHotelDetail(@NonNull Long id) {
        bookingMetrics.onDetailRequest(); // todo metrics
        return hotelRepository.findById(id)
                .map(hotelMapper::toDetailDto);
    }


    @Transactional
    public HotelDTO<AddressDTO> createHotel(HotelCreateRequest request) {
        Hotel hotel = hotelMapper.toEntity(request);
        Hotel saved = hotelRepository.save(hotel);
        return hotelMapper.toDetailDto(saved);
    }

    @Transactional
    @NonNull
    public Optional<List<String>> addAmenitiesToHotel(@NonNull Long hotelId, @NonNull List<String> amenitiesNames) {
        Optional<Hotel> optionalHotel = hotelRepository.findById(hotelId);

        if (optionalHotel.isEmpty()) {
            return Optional.empty();
        }

        Hotel hotel = optionalHotel.get();

        Set<Amenity> amenitiesToAdd = amenitiesNames.stream()
                .map(name -> amenityRepository.findByNameIgnoreCase(name)
                        .orElseGet(() -> Amenity.builder().name(name).build()))
                .collect(Collectors.toSet());

        hotel.getAmenities().addAll(amenitiesToAdd);

        hotelRepository.save(hotel);

        List<String> updatedAmenities = hotel.getAmenities().stream()
                .map(Amenity::getName)
                .collect(Collectors.toList());

        return Optional.of(updatedAmenities);
    }
}
