package com.jobNode.jobber.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Builder
@Setter
@Getter
@ToString
public class AddressRequest {
    @Pattern(regexp = "^[0-9]+", message="Invalid housenumber provided")
    private String housenumber;
    @NotBlank(message = "Street name cannot be blank")
    private String street;
    @NotBlank(message="state cannot be blank")
    private String state;
    @NotBlank(message = "LGA cannot be blank")
    private String lga;
}
