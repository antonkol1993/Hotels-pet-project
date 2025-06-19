package com.hotel.model.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "arrival_time")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ArrivalTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String checkIn;

    private String checkOut;
}
