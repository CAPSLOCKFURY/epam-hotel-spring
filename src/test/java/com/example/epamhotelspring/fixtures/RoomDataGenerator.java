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
            Room room = new Room().setNumber(i).setName(String.valueOf(i)).setCapacity(i)
                    .setRoomClass(roomClass).setPrice(new BigDecimal(100 * i));
            rooms.add(room);
        }
        return rooms;
    }


    private RoomDataGenerator(){}
}
