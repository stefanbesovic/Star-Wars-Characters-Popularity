package com.axiomq.starwars.jwt;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class JwtEmailPasswordRequest {
    private String email;
    private String password;
}
