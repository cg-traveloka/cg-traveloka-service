package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.hotel.Hotel;
import com.cgtravelokaservice.entity.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRepo extends JpaRepository <Room, Integer> {
    List <Room> findAllByHotel(Hotel hotel);
}
