package com.cgtravelokaservice.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class ReviewRequestDTO {
    private Integer contractId;
    @Min(1)
    @Max(10)
    @NotNull
    @NotBlank
    private Double ratingPoint;

    private String comment;

    private List<MultipartFile> images;

    public ReviewRequestDTO(){
        this.images = new ArrayList<>();
    }
}
