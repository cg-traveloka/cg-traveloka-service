package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.room.RoomUtility;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomUtilityRepo extends JpaRepository <RoomUtility, Integer> {
    List<RoomUtility> findAllByRoomUtilityType_Id(Integer roomUtilityTypeId, Sort sort);
}
