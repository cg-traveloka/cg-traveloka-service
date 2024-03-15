package com.cgtravelokaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AirPlaneSearchDto {
    private Integer id;
    private String name;
    private String logoUrl;
}
