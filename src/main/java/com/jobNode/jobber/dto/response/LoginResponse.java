package com.jobNode.jobber.dto.response;

import lombok.Builder;

@Builder
public class LoginResponse {
    private String message;
    private String token;
}
