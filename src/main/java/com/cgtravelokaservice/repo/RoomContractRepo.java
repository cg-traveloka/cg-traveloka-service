package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.booking.RoomContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomContractRepo extends JpaRepository <RoomContract, Integer> {
    List <RoomContract> findAllByRoomId(Integer id);
}