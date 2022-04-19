package com.axiomq.starwars.exceptions;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Schema(description = "Error details template for whole application")
@Data
@Builder
public class ErrorDetails {

    @Schema(description = "Timestamp of error")
    Timestamp timestamp;

    @Schema(description = "Error path")
    String path;

    @Schema(description = "Error message")
    String message;

    @Schema(description = "List of errors")
    Map<String, String> validationErorr;
}
