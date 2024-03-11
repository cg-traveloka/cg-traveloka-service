package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.room.BedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BedTypeRepo extends JpaRepository <BedType, Integer> {
}
