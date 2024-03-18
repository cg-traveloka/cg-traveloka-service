package com.cgtravelokaservice.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComboHasSeatDTO {
    private Integer seatId;
    private HotelSearchDTO hotelSearchDTO;
    private Integer page;
}
