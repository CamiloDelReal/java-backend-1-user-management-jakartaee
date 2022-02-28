package org.xapps.services.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginRequest {
    @JsonbProperty(value = "email")
    @NotNull(message = "Email cannot be empty")
    private String email;

    @JsonbProperty(value = "password")
    @NotNull(message = "Password cannot be empty")
    private String password;
}
