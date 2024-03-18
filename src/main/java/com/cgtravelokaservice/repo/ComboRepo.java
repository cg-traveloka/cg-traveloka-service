package com.cgtravelokaservice.repo;

import com.cgtravelokaservice.entity.booking.Combo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ComboRepo extends JpaRepository <Combo, Integer> {
}
