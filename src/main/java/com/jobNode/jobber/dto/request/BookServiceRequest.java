package com.jobNode.jobber.dto.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.jobNode.jobber.data.models.enums.Services;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.lang.annotation.Before;

import java.time.LocalDate;

@Setter
@Getter
@Builder
public class BookServiceRequest {
    private Long id;
    @NotNull
    private Services service;
    @NotNull
    private Long providerId;
    @NotBlank
    private String description;
    @NotNull
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate proposedDate;
}
