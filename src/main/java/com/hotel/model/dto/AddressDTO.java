package com.hotel.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Hotel address")
public class AddressDTO {

    @Schema(description = "House number", example = "25")
    private Integer houseNumber;

    @Schema(description = "Street", example = "Lenina")
    private String street;

    @Schema(description = "City", example = "Minsk")
    private String city;

    @Schema(description = "Country", example = "Belarus")
    private String country;

    @Schema(description = "Postal code", example = "220030")
    private String postCode;
}
