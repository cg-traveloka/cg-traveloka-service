package com.cgtravelokaservice.dto.response;

import com.cgtravelokaservice.entity.airplant.SeatInformation;
import com.cgtravelokaservice.entity.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UnitComboResponeDTO {
    private Hotel hotel;
    private SeatInformation seat;
    private Integer originPrice;
    private Integer comboPrice;
}
