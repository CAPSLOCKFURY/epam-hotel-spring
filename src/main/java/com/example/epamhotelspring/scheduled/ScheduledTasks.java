package com.example.epamhotelspring.scheduled;

import com.example.epamhotelspring.service.admin.AdminBillingService;
import com.example.epamhotelspring.service.admin.AdminRoomRegistryService;
import com.example.epamhotelspring.service.admin.AdminRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {

    private final AdminBillingService adminBillingService;

    private final AdminRoomRegistryService adminRoomRegistryService;

    private final AdminRoomService adminRoomService;

    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void deleteOldBillings(){
        int affectedRows = adminBillingService.deleteOldBillings();
        //TODO add logger
        System.out.println("Deleted old billings number of affected rows: " + affectedRows);
    }

    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void archiveOldRoomRegistries(){
        int affectedRows = adminRoomRegistryService.archiveOldRoomRegistries();
        System.out.println("Archived old room registries number of affected row : " + affectedRows);
    }

    @Scheduled(fixedRate = 2, timeUnit = TimeUnit.HOURS)
    public void updateRoomStatuses(){
        int affectedRows = adminRoomService.updateRoomStatuses();
        System.out.println("Updated room statuses, number of affected rows: " + affectedRows);
    }

    @Autowired
    public ScheduledTasks(AdminBillingService adminBillingService, AdminRoomRegistryService adminRoomRegistryService, AdminRoomService adminRoomService) {
        this.adminBillingService = adminBillingService;
        this.adminRoomRegistryService = adminRoomRegistryService;
        this.adminRoomService = adminRoomService;
    }
}
