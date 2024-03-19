package com.cgtravelokaservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewResponseDTO {
    private String customerName;

    private Double ratingPoint;

    private List<String> images;

    private String comment;

    private Double averageRatingPoint;
}
