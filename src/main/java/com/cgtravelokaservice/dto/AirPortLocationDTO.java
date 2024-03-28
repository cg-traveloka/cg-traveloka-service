package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class AirPortLocationDTO {
    private Integer id;
    private String cityName;
    private String airportLocationName;
}
