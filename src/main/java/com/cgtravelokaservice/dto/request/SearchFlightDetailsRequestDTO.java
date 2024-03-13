package com.cgtravelokaservice.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchFlightDetailsRequestDTO {
    @NotNull
    private Integer fromAirportLocationId;
    @NotNull
    private Integer toAirportLocationId;
    @NotNull
    private LocalDateTime startTime;
    @Nullable
    private Integer airPlantBrandId;
    @NotNull
    private Integer seatTypeId;
    @NotNull
    private Integer seatQuantity;

    @Nullable
    private String sortBy;
    @Nullable
    private String order;

    @Nullable
    private Integer durationFrom=0;
    @Nullable
    private Integer durationTo;
    @Nullable
    private Integer priceFrom=0;
    @Nullable
    private Integer priceTo;

}
