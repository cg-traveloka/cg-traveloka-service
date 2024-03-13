package com.cgtravelokaservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class FlightInForShortDescription {
    private String name;
    private String unitPrice;
}
