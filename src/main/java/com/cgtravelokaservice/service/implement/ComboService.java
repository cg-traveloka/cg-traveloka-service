package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.booking.Combo;
import com.cgtravelokaservice.repo.ComboRepo;
import com.cgtravelokaservice.service.IComboService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComboService implements IComboService {
    @Autowired
    ComboRepo comboRepo;

    public Combo saveCombo(Combo combo) {
        combo.setStatus("pending");
        return comboRepo.save(combo);
    }
}
