package com.example.epamhotelspring.service.admin;

import com.example.epamhotelspring.dto.RefundDeleteDTO;
import com.example.epamhotelspring.dto.RoomRegistryRefundDTO;
import com.example.epamhotelspring.forms.CloseRoomForm;
import com.example.epamhotelspring.model.Room;
import com.example.epamhotelspring.model.User;
import com.example.epamhotelspring.model.enums.RoomStatus;
import com.example.epamhotelspring.repository.*;
import com.example.epamhotelspring.repository.admin.AdminRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.epamhotelspring.utils.DateUtils.diffInDays;

@Service
public class AdminRoomService {

    private RoomRepository roomRepository;

    private AdminRoomRepository adminRoomRepository;

    private UserRepository userRepository;

    private BillingRepository billingRepository;

    private RoomRegistryRepository roomRegistryRepository;

    private RoomRequestRepository roomRequestRepository;

    @Autowired
    @Lazy
    private AdminRoomService self;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public int updateRoomStatuses(){
        return roomRepository.updateRoomStatuses();
    }

    @Transactional
    public void closeRoom(CloseRoomForm closeRoomForm){
        self.refundMoney(closeRoomForm.getRoomId(), closeRoomForm.getStartDate(), closeRoomForm.getEndDate());
        Room room = roomRepository.getById(closeRoomForm.getRoomId());
        room.setRoomStatus(RoomStatus.UNAVAILABLE);
        roomRepository.save(room);
        List<RefundDeleteDTO> deleteList = adminRoomRepository.findIdForDeletionAfterRefund(closeRoomForm.getRoomId(), closeRoomForm.getStartDate(), closeRoomForm.getEndDate());
        List<Long> roomRegistryIds = new ArrayList<>();
        List<Long> roomRequestIds = new ArrayList<>();
        List<Long> billingIds = new ArrayList<>();
        deleteList.forEach(r -> {
            if(r.getRoomRegistryId() != null){
                roomRegistryIds.add(r.getRoomRegistryId());
            }
            if(r.getBillingId() != null){
                billingIds.add(r.getBillingId());
            }
            if(r.getRoomRequestId() != null){
                roomRequestIds.add(r.getRoomRequestId());
            }
        });
        roomRegistryRepository.deleteByIdIn(roomRegistryIds);
        roomRequestRepository.deleteByIdIn(roomRequestIds);
        billingRepository.deleteByIdIn(billingIds);
    }

    @Transactional
    public void openRoom(Long id){
        Room room = roomRepository.getById(id);
        room.setRoomStatus(RoomStatus.FREE);
        roomRepository.save(room);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    public void refundMoney(Long roomId, LocalDate startDate, LocalDate endDate){
        List<RoomRegistryRefundDTO> refunds = adminRoomRepository.findRoomRegistriesForRefund(roomId, startDate, endDate);
        Map<User, BigDecimal> refundSumMap = refunds.stream().collect(Collectors.toMap(
                        RoomRegistryRefundDTO::getUser,
                        r-> r.getRoomPrice().multiply(new BigDecimal(diffInDays(r.getCheckInDate(), r.getCheckOutDate()))),
                        BigDecimal::add
                )
        );
        refundSumMap.forEach((user, refundSum) -> user.setBalance(user.getBalance().add(refundSum)));
        userRepository.saveAll(refundSumMap.keySet());
    }


    @Autowired
    public void setRoomRepository(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Autowired
    public void setAdminRoomRepository(AdminRoomRepository adminRoomRepository) {
        this.adminRoomRepository = adminRoomRepository;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setBillingRepository(BillingRepository billingRepository) {
        this.billingRepository = billingRepository;
    }

    @Autowired
    public void setRoomRegistryRepository(RoomRegistryRepository roomRegistryRepository) {
        this.roomRegistryRepository = roomRegistryRepository;
    }

    @Autowired
    public void setRoomRequestRepository(RoomRequestRepository roomRequestRepository) {
        this.roomRequestRepository = roomRequestRepository;
    }

}
