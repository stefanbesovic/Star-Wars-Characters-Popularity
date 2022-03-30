package com.axiomq.starwars.exceptions;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

@Data
@Builder
public class ElementNotFoundDetails {
    Timestamp timestamp;
    String path;
    String message;
}
