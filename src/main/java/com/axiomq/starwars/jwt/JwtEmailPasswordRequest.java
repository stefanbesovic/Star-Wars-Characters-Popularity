package com.axiomq.starwars.jwt;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class JwtEmailPasswordRequest {
    private String email;
    private String password;
}
