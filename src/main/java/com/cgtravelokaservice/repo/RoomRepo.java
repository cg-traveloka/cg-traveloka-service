package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepo extends JpaRepository <Room, Integer> {
}
