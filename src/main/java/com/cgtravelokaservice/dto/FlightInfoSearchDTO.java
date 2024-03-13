package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInfoSearchDTO {
    private Integer id;
    @NotNull
    private AirPlantSearchDTO airPlant;
    @NotNull
    private Integer fromAirportLocationId;
    @NotNull
    private Integer toAirportLocationId;
    @NotNull
    private LocalDateTime startTime;
    @NotNull LocalDateTime endTime;
    @NotNull
    private Integer seatQuantity;
    @NotNull
    private String seatTypeName;
    @NotNull
    private Long timeInterval;
    @NotNull
    private Integer unitPrice;
}
