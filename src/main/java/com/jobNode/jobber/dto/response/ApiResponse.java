package com.jobNode.jobber.dto.response;

import lombok.Builder;

@Builder
public class ApiResponse<T>{
    private T data;
    private int code;
    private boolean success;
}
