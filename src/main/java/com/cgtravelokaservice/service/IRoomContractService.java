package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.booking.RoomContract;

public interface IRoomContractService {
    boolean isContractValid(RoomContract roomContract);
}
