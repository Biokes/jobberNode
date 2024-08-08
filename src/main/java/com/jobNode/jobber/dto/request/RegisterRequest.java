package com.jobNode.jobber.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class RegisterRequest {
    @NotBlank
    private String fullname;
    @NotBlank
    private String username;
    @Pattern(regexp = "([0-9A-Za-z,./_-]){8,18}")
    private String password;
//    @Pattern(regexp = "^[a-zA-z][a-zA-Z0-9-.,_:;]+@([a-zA-Z.,/@][])")
    private String email;
    @JsonIgnore
    private AddressRequest address;

}
