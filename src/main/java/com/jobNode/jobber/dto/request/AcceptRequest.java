package com.jobNode.jobber.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AcceptRequest {
    @NotNull
    private Long orderId;
}
