package com.example.epamhotelspring.scheduled;

import com.example.epamhotelspring.service.admin.AdminBillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTasks {

    private final AdminBillingService adminBillingService;

    @Scheduled(fixedRate = 12, timeUnit = TimeUnit.HOURS)
    public void deleteOldBillings(){
        int affectedRows = adminBillingService.deleteOldBillings();
        //TODO add logger
        System.out.println("Deleted old billings number of affected rows: " + affectedRows);
    }

    @Autowired
    public ScheduledTasks(AdminBillingService adminBillingService) {
        this.adminBillingService = adminBillingService;
    }
}
