package com.jobNode.jobber.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessages {
    BOOKED("Your request is sent"),
    INVALID_DETAILS("Ivalid Details provided");
    final String message;
    ExceptionMessages(String message){
        this.message= message;
    }
}
