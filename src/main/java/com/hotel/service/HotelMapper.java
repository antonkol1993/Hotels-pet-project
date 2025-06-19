package com.hotel.service;

import com.hotel.model.dto.*;
import com.hotel.model.entity.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class HotelMapper {

    public Hotel toEntity(HotelCreateRequest dto) {
        return Hotel.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .brand(dto.getBrand())
                .address(Address.builder()
                        .houseNumber(dto.getAddress().getHouseNumber())
                        .street(dto.getAddress().getStreet())
                        .city(dto.getAddress().getCity())
                        .country(dto.getAddress().getCountry())
                        .postCode(dto.getAddress().getPostCode())
                        .build())
                .contacts(Contacts.builder()
                        .phone(dto.getContacts().getPhone())
                        .email(dto.getContacts().getEmail())
                        .build())
                .arrivalTime(ArrivalTime.builder()
                        .checkIn(dto.getArrivalTime().getCheckIn())
                        .checkOut(dto.getArrivalTime().getCheckOut())
                        .build())
                .build();
    }

    public HotelDTO<String> toBriefDto(Hotel hotel) {
        return HotelDTO.<String>builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .address(buildFullAddress(hotel.getAddress()))
                .phone(Optional.of(hotel).map(Hotel::getContacts).map(Contacts::getPhone).orElse(null))
                .build();
    }

    public HotelDTO<AddressDTO> toDetailDto(Hotel hotel) {
        return HotelDTO.<AddressDTO>builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .description(hotel.getDescription())
                .brand(hotel.getBrand())
                .address(toAddressDto(hotel.getAddress()))
                .contacts(toContactsDto(hotel.getContacts()))
                .arrivalTime(toArrivalTimeDto(hotel.getArrivalTime()))
                .amenities(toAmenityNames(hotel.getAmenities()))
                .build();
    }

    private String buildFullAddress(Address address) {
        if (address == null) return null;

        StringBuilder sb = new StringBuilder();

        appendIfPresent(sb, address.getHouseNumber());
        appendIfPresent(sb, address.getStreet());
        appendIfPresent(sb, address.getCity());
        appendIfPresent(sb, address.getPostCode());
        appendIfPresent(sb, address.getCountry());

        return sb.toString();
    }

    private void appendIfPresent(StringBuilder sb, Object value) {
        if (value == null) return;

        if (value instanceof Integer && ((Integer) value) == 0) {
            return;
        }

        String str = value.toString().trim();
        if (str.isEmpty()) return;

        if (!sb.isEmpty()) {
            sb.append(", ");
        }

        sb.append(str);
    }


    private ContactsDTO toContactsDto(Contacts contacts) {
        if (contacts == null) return null;
        return new ContactsDTO(contacts.getPhone(), contacts.getEmail());
    }

    private ArrivalTimeDTO toArrivalTimeDto(ArrivalTime time) {
        if (time == null) return null;
        return new ArrivalTimeDTO(time.getCheckIn(), time.getCheckOut());
    }

    private List<String> toAmenityNames(Set<Amenity> amenities) {
        if (amenities == null || amenities.isEmpty()) return List.of();
        return amenities.stream()
                .map(Amenity::getName)
                .collect(Collectors.toList());
    }

    private AddressDTO toAddressDto(Address address) {
        if (address == null) return null;
        return new AddressDTO(
                address.getHouseNumber(),
                address.getStreet(),
                address.getCity(),
                address.getCountry(),
                address.getPostCode()
        );
    }


}
