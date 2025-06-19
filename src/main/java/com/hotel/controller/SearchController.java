package com.hotel.controller;

import com.hotel.model.dto.HotelDTO;
import com.hotel.model.entity.Hotel;
import com.hotel.service.SearchService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/search")
@Tag(name = "Hotels", description = "API for hotel management")
@RequiredArgsConstructor
public class SearchController implements ISearchController {

    private final SearchService searchService;

    @GetMapping
    @Override
    public List<HotelDTO<String>> searchHotels(
            @RequestParam(name = Hotel.NAME_PROPERTY, required = false) String name,
            @RequestParam(name = Hotel.BRAND_PROPERTY, required = false) String brand,
            @RequestParam(name = Hotel.CITY_PROPERTY, required = false) String city,
            @RequestParam(name = Hotel.COUNTRY_PROPERTY, required = false) String country,
            @RequestParam(name = Hotel.AMENITIES_PROPERTY, required = false) String amenities) {
        return searchService.searchHotels(name, brand, city, country, amenities);
    }
}
