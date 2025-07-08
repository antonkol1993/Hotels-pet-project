package com.hotel.service;

import com.hotel.dao.repository.AmenityRepository;
import com.hotel.dao.repository.HotelRepository;
import com.hotel.metrics.HotelServiceMetrics;
import com.hotel.model.dto.*;
import com.hotel.model.entity.Amenity;
import com.hotel.model.entity.Hotel;
import io.micrometer.core.annotation.Timed;
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
    private final HotelServiceMetrics hotelServiceMetrics;

    @Timed(value = "hotel.getBrief.timer", description = "Timer for getAllHotelsBrief")
    public List<HotelDTO<String>> getAllHotelsBrief() {
        hotelServiceMetrics.onBriefRequest(); // todo metrics
        return hotelRepository.findAll().stream()
                .map(hotelMapper::toBriefDto)
                .collect(Collectors.toList());
    }

    @Timed(value = "hotel.getDetail.timer", description = "Timer for getHotelDetail")
    public Optional<HotelDTO<AddressDTO>> getHotelDetail(@NonNull Long id) {
        hotelServiceMetrics.onDetailRequest(); // todo metrics
        return hotelRepository.findById(id)
                .map(hotelMapper::toDetailDto);
    }

    @Timed(value = "hotel.create.timer", description = "Timer for createHotel")
    @Transactional
    public HotelDTO<AddressDTO> createHotel(HotelCreateRequest request) {
        Hotel hotel = hotelMapper.toEntity(request);
        Hotel saved = hotelRepository.save(hotel);
        HotelDTO<AddressDTO> detailDto = hotelMapper.toDetailDto(saved);
        if (detailDto != null) {
            hotelServiceMetrics.onCreateHotels(); // todo metrics
        }
        return detailDto;
    }

    @Timed(value = "hotel.addAmenities.timer", description = "Timer for addAmenitiesToHotel")
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
        hotelServiceMetrics.onAddedAmenitiesCount(amenitiesToAdd.size()); // todo metrics Quantity of amenities
        hotelServiceMetrics.onAddAmenitiesCall(); // todo metrics Number of times amenities were added to hotels

        List<String> updatedAmenities = hotel.getAmenities().stream()
                .map(Amenity::getName)
                .collect(Collectors.toList());

        return Optional.of(updatedAmenities);
    }
}
