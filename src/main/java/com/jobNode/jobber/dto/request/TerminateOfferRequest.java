package com.jobNode.jobber.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TerminateOfferRequest {
    @NotNull
    private String description;
    private Long providerId;
    private Long orderId;
}
