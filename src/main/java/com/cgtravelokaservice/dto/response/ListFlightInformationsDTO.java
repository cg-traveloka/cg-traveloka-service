package com.cgtravelokaservice.dto.response;

import com.cgtravelokaservice.dto.FlightInfoSearchDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListFlightInformationsDTO {
    List <FlightInfoSearchDTO> flights;
    Integer page;
}
