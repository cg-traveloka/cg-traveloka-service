package com.cgtravelokaservice.entity.airplant;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeatInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "flight_id", referencedColumnName = "id")
    @JsonBackReference
    private FlightInformation flightInformation;
    @ManyToOne
    @JoinColumn(name = "seat_type_id", referencedColumnName = "id")
    private SeatType seatType;

    private Integer quantity;

    private Integer unitPrice;
}
