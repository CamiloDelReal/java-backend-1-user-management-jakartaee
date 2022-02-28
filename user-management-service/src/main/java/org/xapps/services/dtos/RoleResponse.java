package org.xapps.services.dtos;

import jakarta.json.bind.annotation.JsonbProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class RoleResponse {
    @JsonbProperty(value = "id")
    private Long id;

    @JsonbProperty(value = "name")
    private String name;
}
