package com.jobNode.jobber.dto.request;

import com.jobNode.jobber.data.models.enums.Services;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
public class ProviderRequest {
    @NotNull
    private RegisterRequest request;
    private String profilePicsImageLink;
    private String workImageLink1;
    private String workImageLink2;
    private String workImageLink3;
    @NotNull
    private List<Services> servicesList;
}
