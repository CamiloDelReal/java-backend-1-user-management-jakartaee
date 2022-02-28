package org.xapps.services.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class LoginResponse {
    @JsonbProperty(value = "token")
    private String token;

    @JsonbProperty(value = "tokenType")
    private String tokenType;

    @JsonbProperty(value = "validity")
    private Long validity;
}
