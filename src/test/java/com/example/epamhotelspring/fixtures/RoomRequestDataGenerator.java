package com.example.epamhotelspring.fixtures;

import com.example.epamhotelspring.model.RoomClass;
import com.example.epamhotelspring.model.RoomRequest;
import com.example.epamhotelspring.model.User;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class RoomRequestDataGenerator {

    public static Iterable<? extends RoomRequest> generateRoomRequests(User user, RoomClass roomClass, int requestsCount){
        LocalDate today = LocalDate.now();
        LocalDate weekAway = today.plus(7, ChronoUnit.DAYS);
        List<RoomRequest> roomRequests = new LinkedList<>();
        for (int i = 0; i < requestsCount; i++) {
            RoomRequest roomRequest = new RoomRequest(user, roomClass, i, today, weekAway);
            roomRequests.add(roomRequest);
        }
        return roomRequests;
    }

    private RoomRequestDataGenerator(){

    }
}
