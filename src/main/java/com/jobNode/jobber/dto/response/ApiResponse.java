package com.jobNode.jobber.dto.response;

import lombok.*;
import org.springframework.http.HttpStatus;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ApiResponse{
    private Object data;
    private boolean success;
    private HttpStatus status;
}
