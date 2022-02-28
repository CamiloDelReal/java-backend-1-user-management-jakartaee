package org.xapps.services.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "UserRole")
@Table(name = "users_roles")
public class UserRole {
    @EmbeddedId
    private UserRoleId id;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    @EqualsAndHashCode
    @Embeddable
    public static class UserRoleId {
        @Column(name = "user_id")
        private Long userId;

        @Column(name = "role_id")
        private Long roleId;
    }
}
