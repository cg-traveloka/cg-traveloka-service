package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TicketAirPlaneDTO {
    @NotNull
    private Integer seatInfoId;
    @NotNull
    @Min(1)
    private Integer quantity;
}
