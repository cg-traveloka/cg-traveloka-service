package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.room.RoomUtility;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomUtilityRepo extends JpaRepository <RoomUtility, Integer> {
}
