package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@NotNull
public class AirPlantSearchDTO {
    private Integer id;
    private String name;
    private String logoUrl;

}
