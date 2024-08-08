package com.jobNode.jobber.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class ReviewRequest {
    @NotBlank
    private String description;
    @NotNull
    private Long userId;
    @NotNull
    private Long providerId;
}
