package com.cgtravelokaservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboHasSeatAndHotelDTO {
    private Integer seatId;
    private Integer seatQuantity;
    private Integer roomId;
    private Integer roomQuantity;
    private LocalDate startDate;
    private LocalDate endDate;
}
