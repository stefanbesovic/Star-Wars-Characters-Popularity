package com.axiomq.starwars.exceptions;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Map;

@Data
@Builder
public class ErrorDetails {
    Timestamp timestamp;
    String path;
    String message;
    Map<String, String> validationErorr;
}
