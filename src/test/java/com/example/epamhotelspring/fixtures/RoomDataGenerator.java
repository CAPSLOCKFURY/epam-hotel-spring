package com.example.epamhotelspring.fixtures;

import com.example.epamhotelspring.model.Room;
import com.example.epamhotelspring.model.RoomClass;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RoomDataGenerator {

    public static Iterable<? extends Room> generateRooms(int roomCount, RoomClass roomClass){
        List<Room> rooms = new ArrayList<>(roomCount);
        for (int i = 1; i <= roomCount; i++){
            Room room = new Room(i, String.valueOf(i), i, new BigDecimal(100 * i), roomClass);
            rooms.add(room);
        }
        return rooms;
    }


    private RoomDataGenerator(){}
}
