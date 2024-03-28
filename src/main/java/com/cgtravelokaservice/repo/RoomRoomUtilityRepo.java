package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.room.RoomRoomUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomRoomUtilityRepo extends JpaRepository <RoomRoomUtility, Integer> {
    List <RoomRoomUtility> findAllByRoomId(Integer roomId);
}
