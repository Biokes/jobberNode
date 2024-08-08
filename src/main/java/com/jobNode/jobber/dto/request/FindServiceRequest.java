package com.jobNode.jobber.dto.request;

import com.jobNode.jobber.data.models.enums.Services;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class FindServiceRequest {
    @NotNull
    private Long userId;
    @NotNull
    private Services service;
}
