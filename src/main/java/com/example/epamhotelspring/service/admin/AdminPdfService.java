package com.example.epamhotelspring.service.admin;

import com.example.epamhotelspring.dto.RoomRegistryReportDTO;
import com.example.epamhotelspring.repository.admin.AdminRoomRegistryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AdminPdfService {

    private final AdminRoomRegistryRepository adminRoomRegistryRepository;

    public List<RoomRegistryReportDTO> getRoomRegistryReportData(LocalDate checkInDate, LocalDate checkOutDate, Pageable pageable){
        return adminRoomRegistryRepository.findRoomRegistriesForPdfReport(checkInDate, checkOutDate, pageable);
    }

    @Autowired
    public AdminPdfService(AdminRoomRegistryRepository adminRoomRegistryRepository) {
        this.adminRoomRegistryRepository = adminRoomRegistryRepository;
    }

}
