package com.cgtravelokaservice.dto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class TicketAirplaneDto {
    @NotNull
    private Integer seatInfoId;
    @NotNull
    private Integer quantity;
}
