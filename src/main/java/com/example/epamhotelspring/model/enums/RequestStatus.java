package com.example.epamhotelspring.model.enums;

public enum RequestStatus {
    CLOSED, PAID, AWAITING_PAYMENT, AWAITING_CONFIRMATION, AWAITING;

    public static RequestStatus valueOfOrDefault(String value){
        try {
            if(value == null){
                throw new IllegalArgumentException();
            }
            return RequestStatus.valueOf(value);
        } catch (IllegalArgumentException iag){
            return RequestStatus.AWAITING;
        }
    }
}
