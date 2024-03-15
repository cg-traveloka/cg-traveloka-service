package com.cgtravelokaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AirplaneLocationDto {
    private Integer id;
    private String name;
    private String city;
}
