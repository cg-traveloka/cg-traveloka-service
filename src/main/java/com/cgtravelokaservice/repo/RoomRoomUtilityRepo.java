package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.room.RoomRoomUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRoomUtilityRepo extends JpaRepository <RoomRoomUtility, Integer> {
}
