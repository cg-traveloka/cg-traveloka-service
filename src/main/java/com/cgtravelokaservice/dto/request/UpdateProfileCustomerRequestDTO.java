package com.cgtravelokaservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateProfileCustomerRequestDTO {
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
