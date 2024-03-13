package com.cgtravelokaservice.dto.request;

import jakarta.validation.constraints.Future;
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
    @Future
    private LocalDate endDate;
    private Integer quantity;
    private Integer personQuantity;
    private List <Integer> hotelStars;
    private Integer minPrice;
    private Integer maxPrice;
    private String sort;
    private Integer pageNumber;

    public HotelSearchDTO() {
        this.hotelStars = new ArrayList <>();
        for (int i = 1; i <= 5; i++) {
            this.hotelStars.add(i);
        }
        this.minPrice = 0;
        this.maxPrice = 2000000000;
        this.sort = "hotel.hotelBookedNumbers";
        this.pageNumber = 0;
        this.startDate = LocalDate.now();
        this.endDate =
                LocalDate.now().plusDays(1);
        this.quantity = 1;
        this.personQuantity = 1;
    }
}
