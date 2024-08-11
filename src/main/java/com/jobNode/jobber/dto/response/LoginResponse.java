package com.jobNode.jobber.dto.response;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class LoginResponse {
    private String message;
    private String token;
}
