package com.cgtravelokaservice.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@ToString
@Component
public class SearchFlightDetailsRequestDTO {
    @NotNull
    private Integer fromAirportLocationId;
    @NotNull
    private Integer toAirportLocationId;
    @NotNull
//    @FutureOrPresent
    private LocalDate startDate;
    @Nullable
    private List <Integer> airPlantBrandId;
    @NotNull
    private Integer seatTypeId;
    @NotNull
    private Integer seatQuantity;
    @Nullable
    private String sortBy;
    @Nullable
    private String order;
    @Nullable
    private Integer durationFrom;
    @Nullable
    private Integer durationTo;
    @Nullable
    private Integer priceFrom;
    @Nullable
    private Integer priceTo;
    private Integer page;

    public SearchFlightDetailsRequestDTO() {
        this.sortBy = "startTime";
        this.order = "asc";
        this.priceFrom = 0;
        this.durationFrom = 0;
        this.page = 0;
    }

}
