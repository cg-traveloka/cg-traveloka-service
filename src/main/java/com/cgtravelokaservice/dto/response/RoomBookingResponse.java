package com.cgtravelokaservice.dto.response;

import com.cgtravelokaservice.entity.room.Room;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RoomBookingResponse {
    private List <Room> availableRooms;
}
