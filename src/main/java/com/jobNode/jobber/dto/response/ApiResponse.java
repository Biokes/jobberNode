package com.jobNode.jobber.dto.response;

import lombok.Builder;
import org.springframework.http.HttpStatus;

@Builder
public class ApiResponse<T>{
    private T data;
    private int code;
    private boolean success;
    private HttpStatus status;
}
