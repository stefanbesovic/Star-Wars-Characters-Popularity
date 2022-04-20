package com.axiomq.starwars.web.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "User response")
public class UserResponse {

    @Schema(description = "User's id")
    private Long id;

    @Schema(description = "User's username")
    private String username;

    @Schema(description = "User's email")
    private String email;

    @Schema(description = "User's role")
    private String role;
}
