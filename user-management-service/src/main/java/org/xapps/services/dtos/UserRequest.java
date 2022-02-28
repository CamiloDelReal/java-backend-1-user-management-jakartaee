package org.xapps.services.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserRequest {
    @JsonbProperty(value = "email")
    @NotNull(message = "Email cannot be empty")
    private String email;

    @JsonbProperty(value = "password")
    @NotNull(message = "Password cannot be empty")
    private String password;

    @JsonbProperty(value = "firstName")
    @NotNull(message = "First name cannot be empty")
    private String firstName;

    @JsonbProperty(value = "lastName")
    @NotNull(message = "Last name cannot be empty")
    private String lastName;

    @JsonbProperty(value = "roles")
    private List<Long> roles;
}
