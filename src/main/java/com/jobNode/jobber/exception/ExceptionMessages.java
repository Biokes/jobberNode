package com.jobNode.jobber.exception;

import lombok.Getter;

@Getter
public enum ExceptionMessages {
    BOOKED("Your request is sent"),
    SOMETHING_WENT_WRONG("Something went wrong"),
    INVALID_DETAILS("Invalid Details provided. pls check details provided");
    final String message;
    ExceptionMessages(String message){
        this.message= message;
    }
}
