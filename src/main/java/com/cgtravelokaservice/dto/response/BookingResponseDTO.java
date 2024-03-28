package com.cgtravelokaservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingResponseDTO {
    private  Integer contractId;

    private String hotelName;

    private String flightNameFromCity;

    private String flightNameToCity;

    private String status;

    private Integer totalMoney;
}
