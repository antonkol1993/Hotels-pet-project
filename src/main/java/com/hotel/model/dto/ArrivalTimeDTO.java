package com.hotel.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Check-in and check-out times")
public class ArrivalTimeDTO {
    @Schema(description = "Check-in time", example = "14:00")
    private String checkIn;

    @Schema(description = "Check-out time", example = "12:00")
    private String checkOut;
}
