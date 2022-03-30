package com.axiomq.starwars.web.dtos.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {

    @NotEmpty
    @Email(message = "Email must be in correct format.")
    private String email;

    @NotEmpty
    @Size(min = 4, max = 10, message = "Username should be between 4 and 10 characters.")
    private String username;

    @NotEmpty
    @Size(min = 4, max = 12, message = "Password should be between 4 and 12 characters.")
    private String password;
}
