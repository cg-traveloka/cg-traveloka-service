package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelRegisterFormDTO implements Serializable {
    @NotBlank
    private String hotelName;
    @NotBlank
    private String description;
    @NotNull
    private double hotelStar;
    @NotBlank
    private String address;
    @NotNull
    private Integer cityId;
    @NotNull
    private List <Integer> utilitiesId;

    private List <MultipartFile> images;
}
