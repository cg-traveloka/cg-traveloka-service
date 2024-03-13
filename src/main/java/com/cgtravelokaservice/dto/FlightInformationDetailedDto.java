package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightInformationDetailedDto {
    @NotNull
    private Integer flightId;
    @NotNull
    private AirPlantSearchDTO airPlant;
    @NotNull
    private String fromAirportLocationName;
    @NotNull
    private String toAirportLocationName;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private Duration flightDuration;
    @NotNull
    private List<SeatDetailsDto> seatDetails;
}
