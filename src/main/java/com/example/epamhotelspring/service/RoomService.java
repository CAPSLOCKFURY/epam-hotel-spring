package com.example.epamhotelspring.service;

import com.example.epamhotelspring.dto.RoomDTO;
import com.example.epamhotelspring.dto.RoomDetailDTO;
import com.example.epamhotelspring.dto.RoomHistoryDTO;
import com.example.epamhotelspring.forms.BookRoomForm;
import com.example.epamhotelspring.model.Room;
import com.example.epamhotelspring.model.RoomRegistry;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.repository.RoomRegistryRepository;
import com.example.epamhotelspring.repository.RoomRepository;
import com.example.epamhotelspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<RoomDTO> getAllRooms(Pageable pageable){
        return roomRepository.findAllRooms(pageable);
    }

    public RoomDetailDTO getRoomById(Long id){
        RoomDTO roomDetailDTO = roomRepository.findRoomById(id);
        List<RoomRegistry> roomRegistries = roomRegistryRepository.findRoomRegistriesByRoomIdAndArchivedFalse(id);
        return new RoomDetailDTO(roomDetailDTO, roomRegistries);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void bookRoom(BookRoomForm form, User user, BindingResult bindingResult){
        RoomRegistry roomRegistry = new RoomRegistry(form);
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
        roomRepository.unAssignRoomOnOverlappingDates(roomRegistry.getCheckInDate(), roomRegistry.getCheckOutDate());
        userRepository.save(userFromDb);
        roomRegistryRepository.save(roomRegistry);
    }

    public List<RoomHistoryDTO> getUserRoomHistory(Long userId){
        return roomRepository.findUserRoomHistory(userId);
    }

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomRegistryRepository roomRegistryRepository, UserRepository userRepository) {
        this.roomRepository = roomRepository;
        this.roomRegistryRepository = roomRegistryRepository;
        this.userRepository = userRepository;
    }
}
