package com.cgtravelokaservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComboRequestDTO {
    private SearchFlightDetailsRequestDTO
            searchFlightDetailsRequestDTO;
    private HotelSearchDTO hotelSearchDTO;
    private Integer page;
}
