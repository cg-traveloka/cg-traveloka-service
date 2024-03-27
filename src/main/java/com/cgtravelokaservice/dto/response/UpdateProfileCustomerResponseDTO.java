package com.cgtravelokaservice.dto.response;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileCustomerResponseDTO {
    private Integer customerId;

    private String name;

    private String gender;
    @Min(1)
    @Max(31)
    private Integer date;
    @Min(1)
    @Max(12)
    private Integer month;

    private Integer year;
}
