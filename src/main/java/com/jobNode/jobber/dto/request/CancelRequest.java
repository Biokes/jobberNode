package com.jobNode.jobber.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CancelRequest {
    @NotNull
    private Long providerId;
    @NotNull
    private Long userId;
    @NotNull
    private Long orderId;
    @NotBlank
    private String reason;
}
