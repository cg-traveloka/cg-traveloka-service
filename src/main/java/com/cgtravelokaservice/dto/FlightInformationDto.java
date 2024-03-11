package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlightInformationDto implements Serializable {
    @NotNull
    private Integer airplaneBrandId;
    @NotNull
    private Integer fromAirportLocationId;
    @NotNull
    private Integer toAirportLocationId;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    private LocalDateTime endTime;
    @NotNull
    private Integer normalSeatQuantity;
    @NotNull
    private Integer normalSeatPrice;
    @NotNull
    private Integer specialNormalSeatQuantity;
    @NotNull
    private Integer specialNormalSeatPrice;
    @NotNull
    private Integer businessSeatQuantity;
    @NotNull
    private Integer businessSeatPrice;
    @NotNull
    private Integer vipSeatQuantity;
    @NotNull
    private Integer vipSeatPrice;
}