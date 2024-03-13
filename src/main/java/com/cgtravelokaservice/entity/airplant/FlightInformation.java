package com.cgtravelokaservice.entity.airplant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "from_airport_location_id", referencedColumnName = "id")
    private AirPortLocation fromAirPortLocation;
    @ManyToOne
    @JoinColumn(name = "to_airport_location_id", referencedColumnName = "id")
    private AirPortLocation toAirPortLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @ManyToOne
    @JoinColumn(name = "air_plant_brand_id", referencedColumnName = "id")
    private AirPlantBrand airPlantBrand;


}
