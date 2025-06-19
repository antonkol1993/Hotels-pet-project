package com.hotel.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Contact information")
public class ContactsDTO {
    @Schema(description = "Phone number", example = "+375 29 123-45-67")
    private String phone;

    @Schema(description = "Email address", example = "hotel@example.com")
    private String email;
}
