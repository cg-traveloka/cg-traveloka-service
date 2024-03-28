package com.cgtravelokaservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomBookingRequestDTO {
    private LocalDate startDate;

    private Integer nights;

    private Integer personQuantity;

    private Integer roomQuantity;

    private Integer hotelId;
}
