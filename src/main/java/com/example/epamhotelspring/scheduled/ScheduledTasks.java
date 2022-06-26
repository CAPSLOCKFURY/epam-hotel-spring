package com.example.epamhotelspring.scheduled;

import com.example.epamhotelspring.service.admin.AdminBillingService;
import com.example.epamhotelspring.service.admin.AdminRoomRegistryService;
import com.example.epamhotelspring.service.admin.AdminRoomService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class ScheduledTasks {

    private final AdminBillingService adminBillingService;

    private final AdminRoomRegistryService adminRoomRegistryService;

    private final AdminRoomService adminRoomService;

    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void deleteOldBillings(){
        int affectedRows = adminBillingService.deleteOldBillings();
        log.info("Deleted old billings number of affected rows: {}", affectedRows);
    }

    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void archiveOldRoomRegistries(){
        int affectedRows = adminRoomRegistryService.archiveOldRoomRegistries();
        log.info("Archived old room registries number of affected row : {}", affectedRows);
    }

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.HOURS)
    public void updateRoomStatuses(){
        int affectedRows = adminRoomService.updateRoomStatuses();
        log.info("Updated room statuses, number of affected rows: {}", affectedRows);
    }

    @Autowired
    public ScheduledTasks(AdminBillingService adminBillingService, AdminRoomRegistryService adminRoomRegistryService, AdminRoomService adminRoomService) {
        this.adminBillingService = adminBillingService;
        this.adminRoomRegistryService = adminRoomRegistryService;
        this.adminRoomService = adminRoomService;
    }
}
