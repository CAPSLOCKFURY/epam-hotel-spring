package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.model.Room;
import com.example.epamhotelspring.model.RoomRegistry;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.repository.RoomRegistryRepository;
import com.example.epamhotelspring.repository.RoomRepository;
import com.example.epamhotelspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;

@Service
public class RoomService {

    private final RoomRepository roomRepository;

    private final RoomRegistryRepository roomRegistryRepository;

    private final UserRepository userRepository;

    public List<RoomDTO> getAllRooms(String locale){
        return roomRepository.findAllRooms(locale, Sort.by(Sort.Direction.ASC, "id"));
    }

    public RoomDetailDTO getRoomById(Long id, String locale){
        RoomDTO roomDetailDTO = roomRepository.findRoomById(id, locale);
        List<RoomRegistry> roomRegistries = roomRegistryRepository.findRoomRegistriesByRoomIdAndArchivedFalse(id);
        return new RoomDetailDTO(roomDetailDTO, roomRegistries);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void bookRoom(RoomRegistry roomRegistry, User user, BindingResult bindingResult){
        roomRegistry.setUser(user);
        Long bookingOverlaps = roomRepository.countRoomOverlaps(roomRegistry.getCheckInDate(), roomRegistry.getCheckOutDate(), roomRegistry.getRoom().getId());
        if(bookingOverlaps != 0){
            bindingResult.reject("errors.datesOverlap");
            return;
        }
        Room room = roomRepository.findById(roomRegistry.getRoom().getId()).orElseThrow(EntityNotFoundException::new);
        User userFromDb = userRepository.findUserById(user.getId());
        BigDecimal roomPrice = room.getPrice();
        long stayDaysCount = Duration.between(roomRegistry.getCheckInDate().atStartOfDay(), roomRegistry.getCheckOutDate().atStartOfDay()).toDays();
        BigDecimal roomPriceForStayDays = roomPrice.multiply(new BigDecimal(stayDaysCount));
        if(userFromDb.getBalance().compareTo(roomPriceForStayDays) < 0) {
            bindingResult.reject("errors.notEnoughMoney");
            return;
        }
        userFromDb.setBalance(userFromDb.getBalance().subtract(roomPriceForStayDays));
        userRepository.save(userFromDb);
        roomRegistryRepository.save(roomRegistry);
    }

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomRegistryRepository roomRegistryRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.roomRegistryRepository = roomRegistryRepository;
        this.userRepository = userRepository;
    }
}
