package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface RoomRepo extends JpaRepository<Room, Integer> {
    List<Room> findByHotelId(Integer hotelId);

    List<Room> findAllByHotel(Hotel hotel);

}
