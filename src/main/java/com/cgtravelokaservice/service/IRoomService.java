package com.cgtravelokaservice.service;

import com.cgtravelokaservice.entity.room.Room;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IRoomService {
    boolean setImgForRoom(Room room, List <MultipartFile> files);
}
