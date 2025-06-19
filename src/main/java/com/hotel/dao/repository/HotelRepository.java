package com.hotel.dao.repository;

import com.hotel.model.entity.Hotel;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

    @EntityGraph(attributePaths = {"address", "contacts", "arrivalTime", "amenities"})
    @NonNull
    Optional<Hotel> findById(Long id);
}