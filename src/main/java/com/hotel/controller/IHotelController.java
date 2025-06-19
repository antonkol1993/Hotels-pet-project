package com.hotel.controller;

import com.hotel.model.dto.AddressDTO;
import com.hotel.model.dto.HotelCreateRequest;
import com.hotel.model.dto.HotelDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface IHotelController {

    @Operation(
            summary = "Get list of all hotels",
            description = "Returns brief information about all hotels"
    )
    @ApiResponse(responseCode = "200", description = "List of hotels",
            content = @Content(mediaType = "application/json",
                    array = @ArraySchema(schema = @Schema(implementation = HotelDTO.class))))
    @GetMapping
    List<HotelDTO<String>> getAllHotels();

    @Operation(
            summary = "Create new hotel",
            description = "Creates a hotel from the provided request data"
    )
    @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Data for creating a new hotel",
            required = true,
            content = @Content(schema = @Schema(implementation = HotelCreateRequest.class))
    )
    @PostMapping
    ResponseEntity<HotelDTO<AddressDTO>> createHotel(@RequestBody HotelCreateRequest request);

    @Operation(
            summary = "Get hotel by ID",
            description = "Returns detailed hotel information by its unique ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Hotel found",
                    content = @Content(schema = @Schema(implementation = HotelDTO.class))),
            @ApiResponse(responseCode = "404", description = "Hotel with the specified ID not found")
    })
    @GetMapping("/{id}")
    ResponseEntity<?> getHotelById(@PathVariable("id") Long id);

    @Operation(
            summary = "Add amenities to a hotel",
            description = "Adds a list of amenities to a hotel by its ID"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Amenities successfully added"),
            @ApiResponse(responseCode = "404", description = "Hotel with the specified ID not found"),
            @ApiResponse(responseCode = "400", description = "Bad request")
    })
    @PostMapping("/{id}/amenities")
    ResponseEntity<?> addAmenitiesToHotel(
            @PathVariable("id") Long id,
            @RequestBody List<String> amenities);
}
