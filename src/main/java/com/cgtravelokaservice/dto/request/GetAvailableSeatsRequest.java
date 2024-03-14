package com.cgtravelokaservice.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetAvailableSeatsRequest {
    @NotNull
    private Integer flightId;
    @Min(1)
    @NotNull
    private Integer seatQuantity;
}
