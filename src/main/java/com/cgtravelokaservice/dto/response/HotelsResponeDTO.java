package com.cgtravelokaservice.dto.response;

import com.cgtravelokaservice.entity.hotel.Hotel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelsResponeDTO {
    private List <Hotel> hotels;
    private Integer pageNumber;
}
