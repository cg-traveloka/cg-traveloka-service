package com.cgtravelokaservice.service.implement;

import com.cgtravelokaservice.entity.room.Room;
import com.cgtravelokaservice.entity.room.RoomImg;
import com.cgtravelokaservice.repo.RoomImgRepo;
import com.cgtravelokaservice.repo.RoomRepo;
import com.cgtravelokaservice.service.IImageService;
import com.cgtravelokaservice.service.IRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService implements IRoomService {
    @Autowired
    private IImageService imageService;
    @Autowired
    private RoomImgRepo roomImgRepo;
    @Autowired
    private RoomRepo roomRepo;

    public boolean setImgForRoom(Room room, List <MultipartFile> files) {
        try {
            List <RoomImg> roomImgs =
                    new ArrayList <>();
            for (MultipartFile file : files) {
                String url =
                        imageService.save(file);
                RoomImg roomImg = new RoomImg();
                roomImg.setRoom(room);
                roomImg.setUrl(url);
                roomImgs.add(roomImg);
            }
            roomImgRepo.saveAllAndFlush(roomImgs);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}
