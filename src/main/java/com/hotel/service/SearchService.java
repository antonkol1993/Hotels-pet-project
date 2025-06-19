package com.hotel.service;

import com.hotel.dao.repository.HotelRepository;
import com.hotel.dao.specification.HotelSpecification;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class SearchService {

    private final HotelRepository hotelRepository;
    private final HotelMapper hotelMapper;
    private final HotelSpecification hotelSpecification;


    public List<HotelDTO<String>> searchHotels(
            String name, String brand, String city,
            String country, String amenities) {

        Specification<Hotel> hotelParameters = Specification.allOf(hotelSpecification.nameContains(name),
                hotelSpecification.brandContains(brand),
                hotelSpecification.cityContains(city),
                hotelSpecification.countryContains(country),
                hotelSpecification.hasAmenity(amenities));

        List<Hotel> hotels = hotelRepository.findAll(hotelParameters);

        return hotels.stream()
                .map(hotelMapper::toBriefDto)
                .collect(Collectors.toList());
    }

}
