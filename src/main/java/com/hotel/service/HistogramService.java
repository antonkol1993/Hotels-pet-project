package com.hotel.service;

import com.hotel.dao.repository.HotelRepository;
import com.hotel.model.entity.Amenity;
import com.hotel.model.entity.Hotel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class HistogramService {

    private final HotelRepository hotelRepository;
    public Map<String, Long> getHistogram(String param) {

        return switch (param) {
            case Hotel.BRAND_PROPERTY -> hotelRepository.findAll().stream()
                    .filter(h -> h.getBrand() != null)
                    .collect(Collectors.groupingBy(Hotel::getBrand, TreeMap::new, Collectors.counting()));

            case Hotel.CITY_PROPERTY -> hotelRepository.findAll().stream()
                    .filter(h -> h.getAddress() != null && h.getAddress().getCity() != null)
                    .collect(Collectors.groupingBy(h -> h.getAddress().getCity(), TreeMap::new, Collectors.counting()));

            case Hotel.COUNTRY_PROPERTY -> hotelRepository.findAll().stream()
                    .filter(h -> h.getAddress() != null && h.getAddress().getCountry() != null)
                    .collect(Collectors.groupingBy(h -> h.getAddress().getCountry(), TreeMap::new, Collectors.counting()));

            case Hotel.AMENITIES_PROPERTY -> hotelRepository.findAll().stream()
                    .flatMap(h -> h.getAmenities().stream())
                    .filter(a -> a.getName() != null)
                    .collect(Collectors.groupingBy(Amenity::getName, TreeMap::new, Collectors.counting()));

            default -> throw new IllegalArgumentException("Unsupported parameter: " + param +
                    ". Use: brand, city, country, amenities.");
        };
    }
}
