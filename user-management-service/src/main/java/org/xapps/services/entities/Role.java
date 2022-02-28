package org.xapps.services.entities;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity(name = "Role")
@Table(name = "roles")
public class Role {
    public static final String ADMINISTRATOR = "Administrator";
    public static final String GUEST = "Guest";

    @Id
    @TableGenerator(name = "role_id_table_generator", table = "roles_id_generator", pkColumnName = "id_pk_generator", valueColumnName = "id_value_generator", pkColumnValue = "roless_id_generator", initialValue = 10, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    public Role(String name) {
        this.name = name;
    }
}
