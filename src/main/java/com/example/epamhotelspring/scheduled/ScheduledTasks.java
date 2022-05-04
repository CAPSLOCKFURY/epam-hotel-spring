package com.example.epamhotelspring.scheduled;

import com.example.epamhotelspring.service.admin.AdminBillingService;
import com.example.epamhotelspring.service.admin.AdminRoomRegistryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {

    private final AdminBillingService adminBillingService;

    private final AdminRoomRegistryService adminRoomRegistryService;

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

    @Autowired
    public ScheduledTasks(AdminBillingService adminBillingService, AdminRoomRegistryService adminRoomRegistryService) {
        this.adminBillingService = adminBillingService;
        this.adminRoomRegistryService = adminRoomRegistryService;
    }
}
