package com.jobNode.jobber.dto.response;

import com.jobNode.jobber.data.models.enums.Services;
import lombok.*;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProviderResponse {
    private RegisterResponse response;
    private Long id;
    private List<Services> services;
}
