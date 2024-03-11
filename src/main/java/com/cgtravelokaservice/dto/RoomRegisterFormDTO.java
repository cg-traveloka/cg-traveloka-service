package com.cgtravelokaservice.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomRegisterFormDTO {
    @NotNull
    private Integer hotelId;
    @NotNull
    private Integer roomTypeId;
    @NotNull
    @Min(0)
    @Max(1000)
    private Integer quantity;
    @Min(0)
    @NotNull
    private Integer unitPriceOrigin;
    @Min(0)
    @NotNull
    private Integer unitPriceSell;
    @Min(0)
    @NotNull
    private Integer maxPerson;
    @Min(0)
    @NotNull
    private Double size;
    @NotNull
    private Integer bedTypeId;
    @NotNull
    private List <Integer> roomUtilityId;
    private List <MultipartFile> images;
}
