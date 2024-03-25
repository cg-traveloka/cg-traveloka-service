package com.cgtravelokaservice.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class HotelSearchDTO {
    @NotNull
    private Integer cityId;
    @FutureOrPresent
    private LocalDate startDate;
    private Integer nights;
    private Integer roomQuantity;
    private Integer personQuantity;
    private List <Integer> stars;
    private Integer moneyFrom;
    private Integer moneyTo;
    private String sort;
    private Integer pageNumber;

    public HotelSearchDTO() {
        this.stars = new ArrayList <>();
        for (int i = 1; i <= 5; i++) {
            this.stars.add(i);
        }
        this.moneyFrom = 0;
        this.moneyTo = 2000000000;
        this.sort = "booked";
        this.pageNumber = 0;
        this.startDate = LocalDate.now();
        this.nights = 1;
        this.roomQuantity = 1;
        this.personQuantity = 1;
    }
}
