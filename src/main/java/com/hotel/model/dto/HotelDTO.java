package com.hotel.model.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.hotel.model.entity.Address;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@With
@Schema(name = "HotelDTO", description = "The Information about the hotel")
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class HotelDTO<T> {

    @Schema(description = "Hotel identifier", example = "123")
    private Long id;

    @Schema(description = "Hotel name", example = "Hotel Minsk")
    private String name;

    @Schema(description = "Hotel description", example = "Cozy hotel in the city center")
    private String description;

    @Schema(description = "Hotel brand", example = "MinskBrand")
    private String brand;

    @Schema(description = "Phone", example = "+375 29 123-45-67")
    private String phone;

    @Schema(description = "Address can be string or object",
            anyOf = {String.class, AddressDTO.class})
    private T address;

    @Schema(description = "Hotel contacts (email, site, social media)")
    private ContactsDTO contacts;

    @Schema(description = "Arrival and check-in time for guests")
    private ArrivalTimeDTO arrivalTime;

    @Schema(description = "List of available amenities in hotel", example = "[\"WiFi\", \"Parking\", \"Pool\"]")
    private List<String> amenities;
}

