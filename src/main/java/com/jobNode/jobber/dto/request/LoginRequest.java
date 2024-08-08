package com.jobNode.jobber.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
}
