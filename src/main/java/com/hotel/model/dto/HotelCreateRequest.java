package com.hotel.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(name = "HotelCreateRequest", description = "Data for creating a new hotel")
public class HotelCreateRequest {

    @Schema(description = "Hotel name", example = "Grand Hotel")
    private String name;

    @Schema(description = "Hotel brand", example = "Hilton")
    private String brand;

    @Schema(description = "Hotel description", example = "Comfortable hotel in the city center")
    private String description;

    @Schema(description = "City where the hotel is located", example = "Moscow")
    private String city;

    @Schema(description = "Country where the hotel is located", example = "Russia")
    private String country;

    @Schema(description = "List of amenities", example = "[\"WiFi\", \"Parking\"]")
    private List<String> amenities;

    @Schema(description = "Hotel address")
    private AddressDTO address;

    @Schema(description = "Hotel contacts")
    private ContactsDTO contacts;

    @Schema(description = "Check-in and check-out times")
    private ArrivalTimeDTO arrivalTime;
}