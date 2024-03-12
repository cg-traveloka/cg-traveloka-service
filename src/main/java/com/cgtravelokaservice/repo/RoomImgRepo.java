package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.room.RoomImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomImgRepo extends JpaRepository <RoomImg, Integer> {
}
