package com.hotel.controller;


import com.hotel.model.dto.AddressDTO;
import com.hotel.model.dto.HotelCreateRequest;
import com.hotel.model.dto.HotelDTO;
import com.hotel.service.HotelService;
import com.hotel.web.ErrorResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
@Tag(name = "Hotels", description = "API for hotel management")
@RequiredArgsConstructor
public class HotelController implements IHotelController {

    private final HotelService hotelService;

    @GetMapping
    @Override
    public List<HotelDTO<String>> getAllHotels() {
        return hotelService.getAllHotelsBrief();
    }

    @PostMapping
    @Override
    public ResponseEntity<HotelDTO<AddressDTO>> createHotel(@RequestBody HotelCreateRequest request) {
        HotelDTO<AddressDTO> dto = hotelService.createHotel(request);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<?> getHotelById(@PathVariable("id") Long id) {
        Optional<HotelDTO<AddressDTO>> optionalDto = hotelService.getHotelDetail(id);
        if (optionalDto.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.of("Hotel not found with id " + id, HttpStatus.NOT_FOUND.value()));
        }
        return ResponseEntity.ok(optionalDto.get());
    }

    @PostMapping("/{id}/amenities")
    @Override
    public ResponseEntity<?> addAmenitiesToHotel(@PathVariable("id") Long id,
                                                 @RequestBody List<String> amenities) {
        Optional<List<String>> updatedAmenitiesOpt = hotelService.addAmenitiesToHotel(id, amenities);
        if (amenities.isEmpty()) {
            return ResponseEntity.badRequest()
                    .body(ErrorResponse.of("Amenities list must not be empty", 400));
        }
        if (updatedAmenitiesOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ErrorResponse.of("Hotel not found with id " +
                            id, HttpStatus.NOT_FOUND.value()));
        }

        return ResponseEntity.ok(updatedAmenitiesOpt.get());
    }

}
