package com.example.epamhotelspring.service.admin;

import com.example.epamhotelspring.repository.RoomRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdminRoomRegistryService {

    private final RoomRegistryRepository roomRegistryRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int archiveOldRoomRegistries(){
        return roomRegistryRepository.archiveOldRoomRegistries();
    }

    @Autowired
    public AdminRoomRegistryService(RoomRegistryRepository roomRegistryRepository) {
        this.roomRegistryRepository = roomRegistryRepository;
    }
}
