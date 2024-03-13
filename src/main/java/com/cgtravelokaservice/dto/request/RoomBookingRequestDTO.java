package com.cgtravelokaservice.dto.request;

import com.cgtravelokaservice.entity.hotel.Hotel;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingRequestDTO {
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private Integer personQuantity;
    @NotNull
    private Integer roomQuantity;
    @NotNull
    private Integer hotelId;
}
