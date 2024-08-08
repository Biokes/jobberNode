package com.jobNode.jobber.dto.request;

import com.jobNode.jobber.data.models.enums.Services;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FindServiceRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Services service;
}
