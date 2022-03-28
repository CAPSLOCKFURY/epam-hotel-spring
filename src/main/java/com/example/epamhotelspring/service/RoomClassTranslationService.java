package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomClassTranslationDTO;
import com.example.epamhotelspring.repository.RoomClassTranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomClassTranslationService {

    private final RoomClassTranslationRepository repository;

    public List<RoomClassTranslationDTO> getRoomClassesByLanguage(String language){
        return repository.findRoomClassTranslationsByLanguage(language);
    }

    @Autowired
    public RoomClassTranslationService(RoomClassTranslationRepository repository) {
        this.repository = repository;
    }
}
