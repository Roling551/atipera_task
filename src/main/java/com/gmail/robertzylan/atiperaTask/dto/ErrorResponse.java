package com.gmail.robertzylan.atiperaTask.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;

    @JsonProperty("Message")
    private String message;
}
