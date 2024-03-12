package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.room.RoomType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomTypeRepo extends JpaRepository <RoomType, Integer> {
}
