package org.xapps.services.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class UserResponse {
    @JsonbProperty(value = "id")
    private Long id;

    @JsonbProperty(value = "email")
    private String email;

    @JsonbProperty(value = "firstName")
    private String firstName;

    @JsonbProperty(value = "lastName")
    private String lastName;

    @JsonbProperty(value = "roles")
    private List<RoleResponse> roles;
}
