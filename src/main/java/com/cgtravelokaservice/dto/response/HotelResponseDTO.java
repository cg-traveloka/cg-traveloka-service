package com.cgtravelokaservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseDTO implements Serializable {
    private Integer id;

    private String hotelName;

    private double hotelStar;

    private Integer hotelBookedNumbers;

    private double averageRatingPoints;

    private String address;
}
