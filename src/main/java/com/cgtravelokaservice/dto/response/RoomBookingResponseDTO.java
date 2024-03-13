package com.cgtravelokaservice.dto.response;

import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoomBookingResponseDTO {
    @NotNull
    private List<Room> availableRooms;

    @NotNull
    private Hotel hotel;
}
