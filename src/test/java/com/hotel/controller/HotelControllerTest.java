package com.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hotel.model.dto.AddressDTO;
import com.hotel.model.dto.HotelCreateRequest;
import com.hotel.model.dto.HotelDTO;
import com.hotel.model.entity.Address;
import com.hotel.service.HotelService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.beans.factory.annotation.Autowired;

@WebMvcTest(controllers = HotelController.class)
public class HotelControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private HotelService hotelService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /hotels - should return list of hotels")
    void testGetAllHotels() throws Exception {
        List<HotelDTO<String>> mockList = List.of(new HotelDTO<>(), new HotelDTO<>());
        Mockito.when(hotelService.getAllHotelsBrief()).thenReturn(mockList);

        mockMvc.perform(get("/hotels"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetHotelById_OK() throws Exception {
        Long id = 1L;
        HotelDTO<AddressDTO> dto = new HotelDTO<>();
        dto.setId(id);
        dto.setName("Test Hotel");

        Mockito.when(hotelService.getHotelDetail(id)).thenReturn(Optional.of(dto));

        mockMvc.perform(get("/hotels/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Test Hotel"));
    }

    @Test
    void testGetHotelById_NotFound() throws Exception {
        Long id = 999L;
        Mockito.when(hotelService.getHotelDetail(id)).thenReturn(Optional.empty());

        mockMvc.perform(get("/hotels/{id}", id))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Hotel not found with id " + id));
    }

    @Test
    @DisplayName("POST /hotels - should create a hotel")
    void testCreateHotel() throws Exception {
        HotelCreateRequest request = new HotelCreateRequest();
        HotelDTO<AddressDTO> dto = new HotelDTO<>();
        Mockito.when(hotelService.createHotel(any())).thenReturn(dto);

        mockMvc.perform(post("/hotels")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("POST /hotels/{id}/amenities - add amenities to hotel success")
    void testAddAmenitiesToHotel_Success() throws Exception {
        Long hotelId = 1L;
        List<String> amenities = List.of("WiFi", "Parking");

        Mockito.when(hotelService.addAmenitiesToHotel(eq(hotelId), anyList()))
                .thenReturn(Optional.of(amenities));

        mockMvc.perform(post("/hotels/{id}/amenities", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenities)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0]").value("WiFi"))
                .andExpect(jsonPath("$[1]").value("Parking"));
    }

    @Test
    @DisplayName("POST /hotels/{id}/amenities - hotel not found")
    void testAddAmenitiesToHotel_NotFound() throws Exception {
        Long hotelId = 99L;
        List<String> amenities = List.of("WiFi");

        Mockito.when(hotelService.addAmenitiesToHotel(eq(hotelId), anyList()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/hotels/{id}/amenities", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(amenities)))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Hotel not found with id " + hotelId));
    }

    @Test
    @DisplayName("POST /hotels/{id}/amenities - empty list")
    void testAddAmenitiesToHotel_EmptyList() throws Exception {
        Long hotelId = 1L;
        List<String> emptyList = List.of();

        mockMvc.perform(post("/hotels/{id}/amenities", hotelId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(emptyList)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Amenities list must not be empty"));
    }
}
