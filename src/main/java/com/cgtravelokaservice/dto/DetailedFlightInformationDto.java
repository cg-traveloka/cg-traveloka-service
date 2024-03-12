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
@NotNull
public class DetailedFlightInformationDto {
    private Integer flightId;
    private AirPlantSearchDTO airPlant;
    private String fromAirportLocationName;
    private String toAirportLocationName;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Duration flightDuration;
    private List<SeatDetailsDto> seatDetails;
}
