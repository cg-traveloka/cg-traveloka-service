package com.cgtravelokaservice.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SearchFlightRequest {
    @NotNull
    @Min(1)
    private Integer fromLocationId;
    @NotNull
    @Min(1)
    private Integer toLocationId;
    @NotNull
    private LocalDateTime startTime;
    @NotNull
    @Min(1)
    private Integer seatTypeId;
    @NotNull
    @Min(1)
    private Integer seatQuantity;
    @Nullable
    private Integer airplaneId;
    @Nullable
    private String sortBy;
    @Nullable
    private String order;
    private Integer durationFrom ;
    private Integer durationTo ;
    private Integer priceFrom ;
    private Integer priceTo;

}
