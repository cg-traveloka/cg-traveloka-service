package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class SeatDetailsDto {
    private String seatTypeName;
    private Integer quantity;
    private Integer unitPrice;
}
