package com.hotel.controller;

import com.hotel.model.dto.HotelDTO;
import com.hotel.model.entity.Hotel;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface ISearchController {
    @Operation(
            summary = "Search hotels by optional filters",
            description = "Returns a list of hotels filtered by name, brand, city, country, or amenities. "
                    + "All parameters are optional and can be combined."
    )
    @ApiResponse(responseCode = "200", description = "List of hotels matching the filters",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = HotelDTO.class))))
    @GetMapping
    List<HotelDTO<String>> searchHotels(
            @Parameter(description = "Hotel name") @RequestParam(name = Hotel.NAME_PROPERTY, required = false) String name,
            @Parameter(description = "Hotel brand") @RequestParam(name = Hotel.BRAND_PROPERTY, required = false) String brand,
            @Parameter(description = "Hotel city") @RequestParam(name = Hotel.CITY_PROPERTY, required = false) String city,
            @Parameter(description = "Hotel country") @RequestParam(name = Hotel.COUNTRY_PROPERTY, required = false) String country,
            @Parameter(description = "Hotel amenities, comma-separated")
            @RequestParam(name = Hotel.AMENITIES_PROPERTY, required = false) String amenities
    );

}
