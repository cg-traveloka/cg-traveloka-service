package com.cgtravelokaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FlightInfoDtoForSearch {
    private Integer flightInfoId;
    private Integer seatInfoId;
    private String airPlaneBrandName;
    private String fromAirportLocation;
    private String toAirportLocation;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Long timeInterval;
    private Integer unitPrice;
    private String seatType;
}
