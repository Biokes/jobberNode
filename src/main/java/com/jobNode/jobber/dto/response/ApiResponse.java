package com.jobNode.jobber.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T>{
    private T data;
    private boolean success;
    private HttpStatus status;
}
